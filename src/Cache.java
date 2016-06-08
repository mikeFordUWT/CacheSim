import java.math.BigInteger;

import enums.MESI;


public class Cache {

	private CacheLine[] myCacheLines;
	private final int myWays;
	private final int myOffset;
	private final int myLatency;
	public Cache myNextLevelCache;
	private PerformanceCounter myPerformanceCounter;
	//cpu is the handler for the shared bus since a shared bus exists on the
	private CPU myCPU;
	
	
	public Cache(int numOfEntries, int cacheLineSize, int numOfWays, int latency, CPU cpu) {
	    myCacheLines = new CacheLine[numOfEntries];
		myLatency = latency;
		myPerformanceCounter = cpu.getPerformanceCounter();
		myCPU = cpu;
		for (int i = 0; i < myCacheLines.length; i++) {
			myCacheLines[i] = new CacheLine(0, MESI.Invalid);
		}
		myWays = numOfWays;
		myOffset = (int) (Math.log(cacheLineSize) / Math.log(2));
		myNextLevelCache = null;
	}

	public void setMyNextLevelCache (Cache nextLevel) {
		myNextLevelCache = nextLevel;
	}

	/**
	 *
	 * @param address The Address we are checking for in the cache.
     */
	public void addressSearch(int address, Core caller) {
		myPerformanceCounter.increaseExecutionTime(myLatency);
		MESI found = hasAddress(address);
		if (found == MESI.Invalid) {
			myPerformanceCounter.incrementMisses();
			if (myNextLevelCache != null) {
				myNextLevelCache.addressSearch(address, caller);
			} else {
				myCPU.memoryRequest(address, caller);
			}
			this.loadCacheLine(address);
		} else {
			myPerformanceCounter.incrementHits();
		}
	}

	private void loadCacheLine(int address) {
		address = address >> myOffset;
		int sets = myCacheLines.length / myWays;
		int idx = (int) (Math.log(sets) / Math.log(2));
		StringBuilder bitMask = new StringBuilder();
		for (int i = 0; i < idx; i++) {
			bitMask.append("1");
		}
		int mask = Integer.parseInt(bitMask.toString(), 2);
		idx = address & mask;
		int tag = ~mask & address;
		CacheLine lru = myCacheLines[idx];
		for (int i = idx; i < idx + myWays; i++) {
			if (lru.myLastAccess > myCacheLines[i].myLastAccess) {
				lru = myCacheLines[i];
			}
		}
		if (lru.getState() == MESI.Modified) {
			myCPU.writeBack();
		}
		lru.setMyTag(tag);
		lru.setMyState(MESI.Shared);
	}

	public MESI hasAddress(int address) {
		MESI rtn = MESI.Invalid;
		address = address >> myOffset;
		int sets = myCacheLines.length / myWays;
		int idx = (int) (Math.log(sets) / Math.log(2));
		StringBuilder bitMask = new StringBuilder();
		for (int i = 0; i < idx; i++) {
			bitMask.append("1");
		}
		int mask = Integer.parseInt(bitMask.toString(), 2);
		idx = address & mask;
		int tag = ~mask & address;
		for (int i = idx; i < idx + myWays; i++) {
			if (myCacheLines[i].getTag() == tag) {
				rtn = myCacheLines[i].getState();
			}
		}
		return rtn;
	}

	public void cacheLineWriteBack(int address) {
		address = address >> myOffset;
		int sets = myCacheLines.length / myWays;
		int idx = (int) (Math.log(sets) / Math.log(2));
		StringBuilder bitMask = new StringBuilder();
		for (int i = 0; i < idx; i++) {
			bitMask.append("1");
		}
		int mask = Integer.parseInt(bitMask.toString(), 2);
		idx = address & mask;
		int tag = ~mask & address;
		for (int i = idx; i < idx + myWays; i++) {
			if (myCacheLines[i].getTag() == tag) {
				myPerformanceCounter.stateChangeIncrement(MESI.Shared, myCacheLines[i].getState());
				myCacheLines[i].setMyState(MESI.Shared);
			}
		}
		myCPU.writeBack();
	}

	protected void writeToCacheLine(int address) {
		address = address >> myOffset;
		int sets = myCacheLines.length / myWays;
		int idx = (int) (Math.log(sets) / Math.log(2));
		StringBuilder bitMask = new StringBuilder();
		for (int i = 0; i < idx; i++) {
			bitMask.append("1");
		}
		int mask = Integer.parseInt(bitMask.toString(), 2);
		idx = address & mask;
		int tag = ~mask & address;
		for (int i = idx; i < idx + myWays; i++) {
			if (myCacheLines[i].getTag() == tag) {
				myPerformanceCounter.stateChangeIncrement(MESI.Modified, myCacheLines[i].getState());
				myCacheLines[i].setMyState(MESI.Modified);
			}
		}
	}

	public void invalidateCacheLine(int address) {
		address = address >> myOffset;
		int sets = myCacheLines.length / myWays;
		int idx = (int) (Math.log(sets) / Math.log(2));
		StringBuilder bitMask = new StringBuilder();
		for (int i = 0; i < idx; i++) {
			bitMask.append("1");
		}
		int mask = Integer.parseInt(bitMask.toString(), 2);
		idx = address & mask;
		int tag = ~mask & address;
		for (int i = idx; i < idx + myWays; i++) {
			if (myCacheLines[i].getTag() == tag && myCacheLines[i].getState() != MESI.Invalid) {
				myPerformanceCounter.stateChangeIncrement(MESI.Invalid, myCacheLines[i].getState());
				myCacheLines[i].setMyState(MESI.Invalid);
			}
		}
	}


	/*
	 * A helper method to convert decimal to hex
	 */
	private int convertToHex(int n){
		String x = Integer.toHexString(n);
		
		return Integer.parseInt(x);
	}
	
	public CacheLine[] getCacheLines(){
		return myCacheLines;
	}
	
	public CacheLine getCacheLineAt(int index){
		return myCacheLines[index];
	}
}


