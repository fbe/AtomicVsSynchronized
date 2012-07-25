package name.felixbecker.synchronizedvsatomic;

public class ExecutionResult {
	
	private final long synchronizedCallMillis;
	private final long unsynchronizedCallMillis;
	private final long atomicCallMillis;
	private final String threadName;
	
	public long getSynchronizedCallMillis() {
		return synchronizedCallMillis;
	}
	
	public long getUnsynchronizedCallMillis() {
		return unsynchronizedCallMillis;
	}
	
	public long getAtomicCallMillis() {
		return atomicCallMillis;
	}
	
	public String getThreadName(){
		return threadName;
	}

	public ExecutionResult(long synchronizedCallMillis,	long unsynchronizedCallMillis, long atomicCallMillis, long iterationsExecuted, String threadName) {
		this.synchronizedCallMillis = synchronizedCallMillis;
		this.unsynchronizedCallMillis = unsynchronizedCallMillis;
		this.atomicCallMillis = atomicCallMillis;
		this.threadName = threadName;
	}

}
