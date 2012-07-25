package name.felixbecker.synchronizedvsatomic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
	
	private static long SINGLE_THREADED_ITERATIONS = 500000000;
	private static long MULTI_THREADED_ITERATIONS = 500000000;
	
	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.singleThreadedPerformanceTest();
		System.out.println("\n---\n");
		main.multiThreadedPerformanceTest();
	}

	private void multiThreadedPerformanceTest() throws Exception {
		
		int numCores = Runtime.getRuntime().availableProcessors();
		long iterationsPerCore = MULTI_THREADED_ITERATIONS/numCores;
		
		System.out.println("Starting multithreaded benchmark using " + MULTI_THREADED_ITERATIONS + " iterations! Performing the test 5 times (give hotspot a chance..)");
		System.out.println("Creating " + numCores + " Threads. Each one is incrementing the counter " + iterationsPerCore + " times!");
		
		final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(numCores, numCores, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(numCores));

		final Counter counter = new Counter();

		for(int j = 0; j < 5; j++){

			System.out.println("\n-- Multithreaded benchmark iteration " + j + " --");

			final Collection<Future<ExecutionResult>> executionResultFutures = new ArrayList<Future<ExecutionResult>>();
			
			for(int i = 0; i < numCores; i++){
				executionResultFutures.add(threadPoolExecutor.submit(new PerformanceTestingCallable(counter, iterationsPerCore)));
			}
			
			final Collection<ExecutionResult> executionResults = new ArrayList<ExecutionResult>();
			
			for(Future<ExecutionResult> f : executionResultFutures){
				executionResults.add(f.get());
			}
			
			benchmarkSummary(counter, executionResults);
		}
		
		threadPoolExecutor.shutdown();
		
		
	}

	private void singleThreadedPerformanceTest() throws Exception {
		
		System.out.println("Starting singlethreaded benchmark using "+SINGLE_THREADED_ITERATIONS+" iterations! Performing the test 5 times (give hotspot a chance..) ");
		
		
		for(int i = 1; i <= 5; i++){
			
			System.out.println("\n-- Singlethreaded benchmark iteration " + i + " --");
			
			final Counter counter = new Counter();
			final PerformanceTestingCallable performanceTestingCallable = new PerformanceTestingCallable(counter, SINGLE_THREADED_ITERATIONS);
			
			ExecutionResult executionResult = performanceTestingCallable.call();
			benchmarkSummary(counter, executionResult);
		}
	}

	public void benchmarkSummary(Counter counter, Collection<ExecutionResult> executionResults){
		System.out.println("\nBenchmark performed. Results:\n");
		
		for(ExecutionResult executionResult : executionResults){
			System.out.println("Thread: " + executionResult.getThreadName());
			System.out.println("unsynchronized time: " + executionResult.getUnsynchronizedCallMillis() + "ms");
			System.out.println("synchronized time: " + executionResult.getSynchronizedCallMillis() + "ms");
			System.out.println("atomic time: " + executionResult.getAtomicCallMillis() + "ms\n");
		}
		System.out.println(counter);
	}
	
	public void benchmarkSummary(Counter counter, ExecutionResult executionResult){
		final Collection<ExecutionResult> result = new ArrayList<ExecutionResult>(1);
		result.add(executionResult);
		benchmarkSummary(counter, result);
	}
}