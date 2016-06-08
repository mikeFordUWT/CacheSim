import enums.MESI;

/*
 * An object the represents a Cache line
 */
public class CacheLine {

	private int myTag;
	private MESI myState;
	public long myLastAccess;
	
	/*
	 * Constructor 
	 */
	public CacheLine(int tag, MESI state){
		myTag = tag;
		myState = state;
		myLastAccess = 0;
	}

	public void setMyState(MESI myState) {
		this.myState = myState;
	}

	public void setMyTag(int myTag) {
		this.myTag = myTag;
	}
	
	public int getTag(){
		return myTag;
	}
	
	public MESI getState(){
		return myState;
	}
	
	
	
}
