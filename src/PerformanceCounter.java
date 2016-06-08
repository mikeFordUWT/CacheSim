
public class PerformanceCounter {

	private int myTotalHits;
	private int myTotalMisses;
	
	private int myL1Hits;
	private int myL2Hits;
	private int myL3Hits;
	
	private int myL1Misses;
	private int myL2Misses;
	private int myL3Misses;
	
	
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
		myTotalHits = 0;
		myTotalMisses = 0;
		myExecutionTime=0;
		
		myL1Hits = 0;
		myL1Misses = 0;
		myL2Hits = 0;
		myL2Misses=0;
		myL3Hits = 0;
		myL3Misses =0;
	}
	
	public void incrementMisses(){
		myTotalMisses++;
		
	}
	
	public void incrementHits(){
		myTotalHits++;
	}
	
	public void incrementL1Hits(){
		myL1Hits++;
		myTotalHits++;
	}
	
	public void incrementL2Hits(){
		myL2Hits++;
		myTotalHits++;
	}
	
	public void incrementL3Hits(){
		myL3Hits++;
		myTotalHits++;
	}
	
	public void incrementL1Misses(){
		myL1Misses++;
		myTotalMisses++;
	}
	
	public void incrementL2Misses(){
		myL2Misses++;
		myTotalMisses++;
	}
	
	public void incrementL3Misses(){
		myL3Misses++;
		myTotalMisses++;
	}
	
	public void increaseExecutionTime(int n){
		myExecutionTime+=n;
	}

	public void stateChangeIncrement(MESI to, MESI from){
		myStateChanges[from.ordinal()][to.ordinal()]++;
		myTotalStateChanges++;
	}

	public int getStateChangesTotal(MESI to, MESI from){
		return myStateChanges[from.ordinal()][to.ordinal()];
	}

	public int getHits(){
		return myTotalHits;
	}

	public int getMisses(){
		return myTotalMisses;
	}

	public int[][] getStateChangesMatrix(){
		return myStateChanges;
	}

	private String printL1Hits(){
		return "L1 Hits: " + myL1Hits +"\n";
 	}
	
	private String printL2Hits(){
		return "L2 Hits: " + myL2Hits + "\n";
	}
	
	private String printL3Hits(){
		return "L3 Hits: " + myL3Hits + "\n";
	}
	
	private String printHits(){
		String toReturn = "TOTAL HITS: " + myTotalHits + "\n";
		return toReturn;
	}
	
	private String printL1Misses(){
		return "L1 Misses: " + myL1Misses + "\n";
	}
	
	private String printL2Misses(){
		return "L2 Misses: " + myL2Misses + "\n";
	}
	
	private String printL3Misses(){
		return "L3 Misses: " + myL3Misses + "\n";
	}

	private String printMisses(){
		String toReturn = "TOTAL MISSES: " + myTotalMisses + "\n";
		return toReturn;
	}

	private String printStateChanges(MESI from, MESI to){
		String toReturn = from + " to " + to + ": " + getStateChangesTotal(to, from) + "\n";
		return toReturn;
	}

	private String printAllStateChanges(){
		StringBuilder sb = new StringBuilder();

		sb.append("STATE CHANGE TOTALS:\n\n");
		//MODIFY
		sb.append(this.printStateChanges(MESI.Modified, MESI.Shared));
		sb.append(this.printStateChanges(MESI.Modified, MESI.Invalid));
		sb.append("\n");

		//SHARED
		sb.append(this.printStateChanges(MESI.Shared, MESI.Modified));
		sb.append(this.printStateChanges(MESI.Shared, MESI.Invalid));
		sb.append("\n");

		//INVALID
		sb.append(this.printStateChanges(MESI.Invalid, MESI.Modified));
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

	private String printHitRate(){
		int misses =myTotalMisses;
		if(misses == 0){
			misses = 1;
		}
		double hitRate = myTotalHits/(myTotalHits+misses);
		return "HIT RATE: "+(hitRate * 100) + "%\n";
	}
	
	private String printMissRate(){
		int misses =myTotalMisses;
		if(misses == 0){
			misses = 1;
		}
		
		double missRate = myTotalMisses/(myTotalHits + misses);
		return "MISS RATE: " + (missRate * 100) + "%\n";
	}
	
	public String printResults(){
		String toReturn = "RESULTS:\n\n" 
				+ printExecutionTime() + "\n"  
				+ printL1Hits() 
				+ printL2Hits() 
				+ printL3Hits() 
				+ printHits()
				+ printHitRate() + "\n"
				+ printL1Misses() 
				+ printL2Misses() 
				+ printL3Misses() 
				+printMisses() 
				+printMissRate() + "\n"
				+ printAllStateChanges() 
				+ printTotalStateChanges();
		return toReturn;

	}
	
}
