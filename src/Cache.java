import com.intellij.openapi.vcs.history.VcsRevisionNumber;

import java.math.BigInteger;

public class Cache {
	private CacheLine[] myCacheLines;
	private final int myWays;
	private final int myOffset;
	private final int myReadLatency;
	private final int myWriteLatency;
	private int mesiChanges;
	private Cache myNextLevelCache;

	//cpu is the handler for the shared bus since a shared bus exists on the
	private CPU myCPU;
	
	
	public Cache(int numOfEntries, int cacheLineSize, int numOfWays, int readLatency, int writeLatency, CPU cpu) {
		mesiChanges = 0;
		myCacheLines = new CacheLine[numOfEntries];
		myReadLatency = readLatency;
		myWriteLatency = writeLatency;
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
	 * @return returns true if the address is found.
     */
		public boolean hasAddress(int address) {
		boolean rtn = false;
		address = address >> myOffset;
		int sets = myCacheLines.length / myWays;


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


