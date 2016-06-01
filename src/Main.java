import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(convertToDecimal(20));
//		System.out.println(convertToHex(20));
		System.out.println(hexToBin(23, 32));
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
