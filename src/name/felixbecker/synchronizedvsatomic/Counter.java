package name.felixbecker.synchronizedvsatomic;

import java.util.concurrent.atomic.AtomicLong;

@NotThreadSafe
public class Counter {
	private long unsynchronizedCount = 0;
	private long synchronizedCount = 0;
	private AtomicLong atomicCount = new AtomicLong(0);

	// test methods
	public synchronized long incrementAndGetSynchronizedCount(){
		return ++synchronizedCount;
	}
	
	public long incrementAndGetUnsynchronizedCount(){
		return ++unsynchronizedCount;
	}
	
	public long incrementAndGetAtomicCount(){
		return atomicCount.incrementAndGet();
	}
	
	// for benchmark results
	public long getUnsynchronizedCount() {
		return unsynchronizedCount;
	}

	public long getSynchronizedCount() {
		return synchronizedCount;
	}

	public long getAtomicCount() {
		return atomicCount.get();
	}
	
	@Override
	public String toString() {
		return "Counter => Synchronized: "+synchronizedCount+" Unsynchronized: "+unsynchronizedCount+" Atomic: "+atomicCount.get()+"";
	}

	
}
