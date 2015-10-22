package rendezvous.federator.executor.cache;

import rendezvous.federator.core.Value;

public class CachedValue {

	private Long cachedSequence;

	private Value value;
	
	public CachedValue(Value value, long cachedSequence) {
		this.value=value;
		this.cachedSequence=cachedSequence;
	}

	public Long getCachedSequence() {
		return cachedSequence;
	}

	public Value getValue() {
		return value;
	}
}
