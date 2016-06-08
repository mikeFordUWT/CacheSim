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

		/* SCAN IN 5k trace*/
		File fiveK = new File("trace-5k.csv");
		ArrayList<MemInstruct> instructs5K = new ArrayList<MemInstruct>();
		parseCSV(instructs5K, fiveK);

		
		System.out.println(instructs5K.size());
		System.out.println(instructs2K.size());
		
		PerformanceCounter pc = new PerformanceCounter();

		CPU cpu = new CPU(2, pc);

		cpu.queueUp(instructs5K);
		cpu.executeLines(50,0);
		for (int i = 0; i < 5000; i++) {
			cpu.executeLines(1,0);
			cpu.executeLines(1,1);
		}
		
		//TODO Code for everything else goes here.
		
		
		System.out.println(pc.printResults());

		
		
		
			
		
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
