/**
 * An object that represents the memory instruction.
 *
 */
public class MemInstruct {
	private int myInstructAdd;
	private int myReadOrWrite;
	private int myDataAddress;
	
	public MemInstruct(int instructAddress, int readOrWrite, int dataAddress){
		myInstructAdd = instructAddress;
		myReadOrWrite = readOrWrite;
		myDataAddress = dataAddress;
	}
	
	public MemInstruct(int instructAddress){
		myInstructAdd = instructAddress;
		myReadOrWrite = -1;
		myDataAddress = -1;
	}
	
	public int getInstruction(){
		return myInstructAdd;
	}
	
	public int getReadOrWrite(){
		return myReadOrWrite;
	}
	
	public int getData(){
		return myDataAddress;
	}

	@Override
	public String toString(){
		String toReturn = myInstructAdd+ ", " + myReadOrWrite + ", " + myDataAddress;
		return toReturn;
	}
}
