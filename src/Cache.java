
public class Cache {
	
	private CacheLine[] myCacheLines;
	
	
	public Cache(int size){
		myCacheLines = new CacheLine[size];
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


