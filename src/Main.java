import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		/* SCAN IN 2K TRACE */
		File twoK = new File("trace-2k.csv");
		ArrayList<MemInstruct> instructs2K = new ArrayList<MemInstruct>();
		parseCSV(instructs2K, twoK);
		PerformanceCounter pc2K = new PerformanceCounter();
		
		
		/* SCAN IN 5k trace*/
		File fiveK = new File("trace-5k.csv");
		ArrayList<MemInstruct> instructs5K = new ArrayList<MemInstruct>();
		parseCSV(instructs5K, fiveK);
		
		PerformanceCounter pc5K = new PerformanceCounter();
		
		
		//TODO Code for everything else goes here.
		
		String hello = pc5K.printResults();
		System.out.println(hello);
		
		
		
		
		
			
		
	}

	private static int convertToDecimal(int n){
		return Integer.valueOf(String.valueOf(n), 16);
	}
	
	private static int convertToHex(int n){
		String x = Integer.toHexString(n);
		
		return Integer.parseInt(x);
	}
	
	private static String hexToBin(int n, int digits){
		String s = String.valueOf(n);
		String toReturn = new BigInteger(s,16).toString(2);
		String format = "%"+digits +"s";
		return String.format(format, Integer.toBinaryString(n)).replace(' ', '0');
	}
	private static void parseCSV(ArrayList<MemInstruct> arrayList, File file){
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNext() ){
				String line = scan.nextLine();
				String[] instruct = line.split(",");
				if(instruct.length>1 && !instruct[1].equals("")){
					int first = Integer.parseInt(instruct[0]);
					int second = Integer.parseInt(instruct[1]);
					int third = Integer.parseInt(instruct[2]);
					arrayList.add(new MemInstruct(first, second, third));
					
				}

			}
			scan.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
