/*
 * An object the represents a Cache line
 */
public class CacheLine {



	private int myTag;
	private MESI myState;
	
	/*
	 * Constructor 
	 */
	public CacheLine(int tag, MESI state){
		myTag = tag;
		myState = state;
	}
	
	
	public int getTag(){
		return myTag;
	}
	
	public MESI getState(){
		return myState;
	}
	
	
	
}
