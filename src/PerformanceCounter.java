import enums.CacheLevel;
import enums.MESI;

public class PerformanceCounter {
	
	private int myHits;
	private int myMisses;
	private int myExecutionTime;
	private int[][] myStateChanges;
	private int myTotalStateChanges;

	private int myL1Hits;
	private int myL2Hits;
	private int myL3Hits;

	private int myL1Misses;
	private int myL2Misses;
	private int myL3Misses;





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
		myL1Hits = 0;
		myL1Misses = 0;
		myL2Hits = 0;
		myL2Misses=0;
		myL3Hits = 0;
		myL3Misses =0;
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

	private String printL1Misses(){
		return "L1 Misses: " + myL1Misses + "\n";
	}

	private String printL2Misses(){
		return "L2 Misses: " + myL2Misses + "\n";
	}

	private String printL3Misses(){
		return "L3 Misses: " + myL3Misses + "\n";
	}

	public void incrementMisses(CacheLevel lvl){
		myMisses++;
		
		switch (lvl) {
		case L1:
			myL1Misses++;
			break;
		case L2:
			myL2Misses++;
			break;
		case L3:
			myL3Misses++;
			break;
		}
	}
	
	public void incrementHits(CacheLevel lvl){
		myHits++;
		
		switch (lvl) {
		case L1:
			myL1Hits++;
			break;
		case L2:
			myL2Hits++;
			break;
		case L3:
			myL3Hits++;
			break;
		}
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
		String toReturn = "RESULTS:\n\n" + printExecutionTime() + "\n"  +
				printL1Hits() + printL2Hits() + printL3Hits() + printHits()+"\n"
				+ printL1Misses() + printL2Misses() + printL3Misses() +printMisses()+"\n"+
				printAllStateChanges() + printTotalStateChanges();
		return toReturn;

	}
	
}
