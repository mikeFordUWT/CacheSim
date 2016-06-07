import java.math.BigInteger;

public class Cache {
	
	private CacheLine[] myCacheLines;
	private final int myWays;
	private final int myOffset;
	private final int myLatency;
	private Cache myNextLevelCache;
	//cpu is the handler for the shared bus since a shared bus exists on the
	private CPU myCPU;
	
	
	public Cache(int numOfEntries, int cacheLineSize, int numOfWays, int latency, CPU cpu) {
		myCacheLines = new CacheLine[numOfEntries];
		myLatency = latency;
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
	public void addressSearch(int address, boolean read) {
		int latencyPenalty = myLatency;
		MESI found = hasAddress(address);
		if (found == MESI.Invalid) {
			if (myNextLevelCache != null) {
				myNextLevelCache.addressSearch(address, read);
			} else {
				myCPU.memoryRequest(address);
			}
		}
	}

	private MESI hasAddress(int address) {
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


