/*
 * An object the represents a Cache line
 */
public class CacheLine {

	private int myTag;
	private int myState;
	
	/*
	 * Constructor 
	 */
	public CacheLine(int tag, int state){
		myTag = tag;
		myState = state;
	}
	
	
	public int getTag(){
		return myTag;
	}
	
	public int getState(){
		return myState;
	}
	
	
	
}
