import java.io.File;
import java.util.List;
import java.util.Scanner;

import enums.MESI;
import enums.WritePolicy;


public class CPU {

	// Path to the config file
	private String cfgPath = "init.cfg";

	// Constants that will be used for handling cores and
	private int CacheLineSize = 16;
	private int L1Size = 32;
	private int L1Latency = 1;
	private int L2Size = 512;
	private int L2Latency = 10;
	private int L3Size = 2048;
	private int L3Latency = 35;
	private int L1MSize = (int) Math.pow(2, 14);
	private int L1MLatency = 100;
	private int L2MSize = (int) (Math.pow(2, 21) - Math.pow(2, 14));
	private int L2MReadLatency = 250;
	private int L2MWriteLatency = 400;
	private int Associativity = 1;
	private WritePolicy Policy = WritePolicy.writeBack;

	private Cache L3;

	private List<MemInstruct> myQueue;

	private Core[] myCores;
	private PerformanceCounter myPerformanceCounter;

	public CPU(int numCores, PerformanceCounter pc) {
		if(numCores < 1) {
			throw new IllegalArgumentException("Cannot have less than one cores.");
		}
		myCores = new Core[numCores];
		myPerformanceCounter =  pc;
		readCFG();
		instantiateCores();
	}

	private void instantiateCores() {

		L3 = new Cache(L3Size, CacheLineSize, Associativity, L3Latency, this);

		for(int i = 0; i < myCores.length; i++) {

			Cache theL1i = new Cache(L1Size, CacheLineSize, Associativity, L1Latency, this);
			Cache theL1d = new Cache(L1Size, CacheLineSize, Associativity, L1Latency, this);

			Cache theL2 = new Cache(L2Size, CacheLineSize, Associativity, L2Latency, this);

			theL1i.setMyNextLevelCache(theL2);
			theL1d.setMyNextLevelCache(theL2);

			theL2.setMyNextLevelCache(L3);

			myCores[i] = new Core(theL1i, theL1d, this);
		}
	}

	private void readCFG() {

		File cfgFile = null;
		Scanner inStream = null;
		boolean Success = false;

		try {

			cfgFile = new File(cfgPath);
			inStream = new Scanner(cfgFile);
		} catch(Exception e) {

			System.out.println("At least one error occured whilst reading in the config file.\n"
					+ "Will continue with the constants specified in the file.\n"
					+ "See init.cfg for more information.");
		}

		while(inStream.hasNextLine()) {
			String line = inStream.nextLine();
			if(line.length() > 0 && line.charAt(0) != '#') {
				setVal(line);
			}
		}
		inStream.close();
	}

	private void setVal(String line) {

		Scanner scn = new Scanner(line);

		String token = scn.next();
		int val = -1;

		switch(token) {

		case "CLS":
			try {
				val = scn.nextInt();
				CacheLineSize = val;
			} catch(Exception e) {

			}
			break;
		case "L1S":
			try {
				val = scn.nextInt();
				L1Size = val;
			} catch(Exception e) {

			}
			break;
		case "L1L":
			try {
				val = scn.nextInt();
				L1Latency = val;
			} catch(Exception e) {

			}
			break;
		case "L2S" :
			try {
				val = scn.nextInt();
				L2Size = val;
			} catch(Exception e) {

			}
			break;
		case "L2L" :
			try {
				val = scn.nextInt();
				L2Latency = val;
			} catch(Exception e) {

			}
			break;
		case "L3S" :
			try {
				val = scn.nextInt();
				L3Size = val;
			} catch(Exception e) {

			}
			break;
		case "L3L" :
			try {
				val = scn.nextInt();
				L3Latency = val;
			} catch(Exception e) {

			}
			break;
		case "L1MS" :
			try {
				val = scn.nextInt();
				L1MSize = (int) Math.pow(2, val);
			} catch(Exception e) {

			}
			break;
		case "L1ML" :
			try {
				val = scn.nextInt();
				L1MLatency = val;
			} catch(Exception e) {

			}
			break;
		case "L2MS" :
			try {
				val = scn.nextInt();
				L2MSize = (int) Math.pow(2, val) - L1Size;
			} catch(Exception e) {

			}
			break;
		case "L2MLR" :
			try {
				val = scn.nextInt();
				L2MReadLatency = val;
			} catch(Exception e) {

			}
			break;
		case "L2MLW" :
			try {
				val = scn.nextInt();
				L2MWriteLatency = val;
			} catch(Exception e) {

			}
			break;
		case "CA" :
			try {
				val = scn.nextInt();
				Associativity = val;
			} catch(Exception e) {

			}
			break;
		case "WP" :
			String policy = scn.next();
			if(policy.equals("wt")) {
				Policy = WritePolicy.writeThrough;
			} else if(policy.equals("wb")) {
				Policy = WritePolicy.writeBack;
			}
			break;
		default :
			break;
 		}
		scn.close();
	}

	public void queueUp(List<MemInstruct> instructions) {
		myQueue = instructions;
		for (Core c : myCores) {
			c.queueUp(myQueue);
		}
	}

	public void invalidateCacheLines(int address, Core caller) {
		L3.invalidateCacheLine(address);
		for (Core c : myCores) {
			if (c != caller) c.invalidate(address);
		}
	}

	public void executeLines(int numOfIntructions, int cpuNumber) {
		for (int i = 0; i < numOfIntructions; i++) {
			Core c = myCores[cpuNumber];
			c.executeLine();
		}
	}

    public void memoryRequest(int memoryAddress, Core caller) {
		//snoop
		boolean l1Hit = false;
		boolean l2Hit = false;
		boolean writeBack = false;
		Core needsToWrite = null;
		for (Core c : myCores) {
			if (!(c == caller)) {
				MESI state = c.myL1D.hasAddress(memoryAddress);
				if (state != MESI.Invalid) {
					l1Hit = true;
					if (state == MESI.Modified) {
						writeBack = true;
						needsToWrite = c;
					}
				} else {
					state = c.myL1I.hasAddress(memoryAddress);
					if (state != MESI.Invalid) {
						l1Hit = true;
					} else {
						state = c.myL1D.myNextLevelCache.hasAddress(memoryAddress);
						if (state != MESI.Invalid) {
							l2Hit = true;
						}
					}
				}
			}
		}

		if (l1Hit) {
			myPerformanceCounter.incrementHits();
			if (writeBack) {
				needsToWrite.myL1D.cacheLineWriteBack(memoryAddress, true);
			}
			myPerformanceCounter.increaseExecutionTime(L1Latency);
		} else if (l2Hit) {
			myPerformanceCounter.incrementHits();
			myPerformanceCounter.incrementMisses();
			myPerformanceCounter.increaseExecutionTime(L2Latency + L1Latency);
		} else {
			readFromMemory(memoryAddress);
		}

    }

	public PerformanceCounter getPerformanceCounter() {

		return myPerformanceCounter;
	}

	public void writeBack(int theAddr) {
		
		if(theAddr > -1 && theAddr <= L1MSize) {
			myPerformanceCounter.increaseExecutionTime(L1MLatency);
		} else if (theAddr > L1MSize && theAddr <= L2MSize) {
			myPerformanceCounter.increaseExecutionTime(L2MWriteLatency);
		}
	}
	public void readFromMemory(int theAddr) {
		if(theAddr > -1 && theAddr <= L1MSize) {
			myPerformanceCounter.increaseExecutionTime(L1MLatency);
		} else if (theAddr > L1MSize && theAddr <= L2MSize) {
			myPerformanceCounter.increaseExecutionTime(L2MReadLatency);
		}
	}
}
