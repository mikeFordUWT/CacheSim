import java.util.List;

public class Core {

	protected Cache myL1D;
	protected Cache myL1I;
	protected CPU myCPU;
	private List<MemInstruct> myQueue;
	private int instructIdx;

	public Core(Cache thel1i, Cache thel1d, CPU theCPU) {
		myL1D = thel1d;
		myL1I = thel1i;
		myCPU = theCPU;
		instructIdx = 0;
	}

	//cache.writeToCacheLine()  then call cpu.invalidateAddress then call myL1D.nextlevelcache.invalidate(address).

	public void executeLine() {
		if (instructIdx < myQueue.size()) {
			MemInstruct current = myQueue.get(instructIdx);
			instructIdx++;
			myL1I.addressSearch(current.getInstruction(), this);
			if (current.getData() >= 0) myL1D.addressSearch(current.getData(), this);
			if (current.getReadOrWrite() > 0) {
				myL1D.writeToCacheLine(current.getData());
				myL1D.myNextLevelCache.invalidateCacheLine(current.getData());
				myCPU.invalidateCacheLines(current.getData(), this);
			}
		}
	}



	public void invalidate(int address) {
		myL1D.invalidateCacheLine(address);
		myL1D.myNextLevelCache.invalidateCacheLine(address);
	}

	public void queueUp(List<MemInstruct> queue) {
		myQueue = queue;
	}
}
