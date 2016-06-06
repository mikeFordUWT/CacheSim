
public class PerformanceCounter {
	private int myHits;
	private int myMisses;
	private int myExecutionTime;
	
	
	public PerformanceCounter(){
		myHits = 0;
		myMisses = 0;
		myExecutionTime=0;
	}
	
	public void incrementMisses(){
		myMisses++;
	}
	
	public void incrementHits(){
		myHits++;
	}
	
	
}
