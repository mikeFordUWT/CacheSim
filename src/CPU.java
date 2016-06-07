import java.io.File;
import java.util.Scanner;

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

	private Core[] myCores;

	public CPU(int numCores) {
		if(numCores < 1) {
			throw new IllegalArgumentException("Cannot have less than one cores.");
		}
		myCores = new Core[numCores];

		readCFG();
		instantiateCores();
	}

	private void instantiateCores() {
		// TODO Auto-generated method stub

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
}