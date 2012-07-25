package name.felixbecker.synchronizedvsatomic;

import java.util.Date;
import java.util.concurrent.Callable;

public class PerformanceTestingCallable implements Callable<ExecutionResult> {

	
	private final Counter counter;
	private final long iterations;

	public PerformanceTestingCallable(Counter counter, long iterations){
		this.counter = counter;
		this.iterations = iterations; 
		
	}
	
	@Override
	public ExecutionResult call() throws Exception {
		
		/* benchmark unsynchronized */
		
		long startTimeUnsynchronized = new Date().getTime();
		
		for(long i = 0; i < iterations; i++){
			counter.incrementAndGetUnsynchronizedCount();
		}
		
		long unsynchronizedResultTime = new Date().getTime() - startTimeUnsynchronized;
		
		/* benchmark synchronized */
		
		long startTimeSynchronized = new Date().getTime();
		
		for(long i = 0; i < iterations; i++){
			counter.incrementAndGetSynchronizedCount();
		}
		long synchronizedResultTime = new Date().getTime() - startTimeSynchronized;
		
		/* benchmark atomic */
		
		long startTimeAtomic = new Date().getTime();
		
		for(long i = 0; i < iterations; i++){
			counter.incrementAndGetAtomicCount();
		}
		
		long atomicResultTime = new Date().getTime() - startTimeAtomic;
		
		
		ExecutionResult executionResult = new ExecutionResult(synchronizedResultTime, unsynchronizedResultTime, atomicResultTime, iterations, Thread.currentThread().getName());
		
		return executionResult;
	}

}
