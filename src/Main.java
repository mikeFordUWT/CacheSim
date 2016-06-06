import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		File twoK = new File("trace-2k.csv");
		Scanner scan = new Scanner(twoK);
		ArrayList<MemInstruct> instructs2K = new ArrayList<MemInstruct>();
		while(scan.hasNext() ){
			String line = scan.nextLine();
			String[] instruct = line.split(",");
			if(instruct.length>1 && !instruct[1].equals("")){
//				System.out.println("Hello");
//				System.out.println(line);
				int first = Integer.parseInt(instruct[0]);
				int second = Integer.parseInt(instruct[1]);
				int third = Integer.parseInt(instruct[2]);
				instructs2K.add(new MemInstruct(first, second, third));
				
			}

		}
		scan.close();
		System.out.println(instructs2K.size());
		
		File fiveK = new File("trace-5k.csv");
		Scanner scan2 = new Scanner(fiveK);
		ArrayList<MemInstruct> instructs5K = new ArrayList<MemInstruct>();
		while(scan2.hasNext()){
			String line = scan2.nextLine();
			String[] instruct = line.split(",");
			if(instruct.length>1 && !instruct[1].equals("")){
				int first = Integer.parseInt(instruct[0]);
				int second = Integer.parseInt(instruct[1]);
				int third = Integer.parseInt(instruct[2]);
				instructs5K.add(new MemInstruct(first, second, third));
			}
			
		}
		scan2.close();
		System.out.println(instructs5K.size());
		
		System.out.println();
		
		
		
		
			
		
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
}
