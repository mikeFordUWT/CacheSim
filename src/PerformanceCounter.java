
public class PerformanceCounter {
	
	private int myHits;
	private int myMisses;
	private int myExecutionTime;
	private int[][] myStateChanges;
	private int myTotalStateChanges;
	
	
	
	
	public PerformanceCounter(){
		myStateChanges = new int[4][4];
		for(int i = 0; i<myStateChanges.length; i++){
			for(int j = 0; j< myStateChanges.length; j++){
				myStateChanges[i][j] = 0;
			}
		}
		myTotalStateChanges=0;
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
	
	public void increaseExecutionTime(int n){
		myExecutionTime+=n;
	}
	
	public void stateChangeIncrement(MESI to, MESI from){
		myStateChanges[to.ordinal()][from.ordinal()]++;
		myTotalStateChanges++;
	}
	
	public int getStateChangesTotal(MESI to, MESI from){
		return myStateChanges[to.ordinal()][from.ordinal()];
	}
	
	public int getHits(){
		return myHits;
	}
	
	public int getMisses(){
		return myMisses;
	}
	
	public int[][] getStateChangesMatrix(){
		return myStateChanges;
	}
	
	private String printHits(){
		String toReturn = "HITS: " + myHits + "\n";
		return toReturn;
	}
	
	private String printMisses(){
		String toReturn = "MISSES: " + myMisses + "\n";
		return toReturn;
	}
	
	private String printStateChanges(MESI to, MESI from){
		String toReturn = to + " to " + from + ": " + getStateChangesTotal(to, from) + "\n";
		return toReturn;
	}
	
	private String printAllStateChanges(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("STATE CHANGE TOTALS:\n\n");
		//MODIFY
		sb.append(this.printStateChanges(MESI.Modified, MESI.Exclusive));
		sb.append(this.printStateChanges(MESI.Modified, MESI.Shared));
		sb.append(this.printStateChanges(MESI.Modified, MESI.Invalid));
		
		//EXCLUSIVE
		sb.append(this.printStateChanges(MESI.Exclusive, MESI.Modified));
		sb.append(this.printStateChanges(MESI.Exclusive, MESI.Shared));
		sb.append(this.printStateChanges(MESI.Exclusive, MESI.Invalid));
		
		//SHARED
		sb.append(this.printStateChanges(MESI.Shared, MESI.Modified));
		sb.append(this.printStateChanges(MESI.Shared, MESI.Exclusive));
		sb.append(this.printStateChanges(MESI.Shared, MESI.Invalid));
		
		//INVALID
		sb.append(this.printStateChanges(MESI.Invalid, MESI.Modified));
		sb.append(this.printStateChanges(MESI.Invalid, MESI.Exclusive));
		sb.append(this.printStateChanges(MESI.Invalid, MESI.Shared));
		
		//return this
		return sb.toString();
		
	}
	
	private String printExecutionTime(){
		String toReturn = "EXECUTION TIME: "+ myExecutionTime + " ns\n";
		return toReturn;
 	}
	
	private String printTotalStateChanges(){
		String toReturn = "\nTOTAL STATE CHANGES: " + myTotalStateChanges;
		return toReturn;
	}
	
	public String printResults(){
		String toReturn = "RESULTS:\n\n"+printHits() + printMisses() + printExecutionTime() +"\n"+ 
				printAllStateChanges() + printTotalStateChanges();
		return toReturn;
		
	}
	
}
