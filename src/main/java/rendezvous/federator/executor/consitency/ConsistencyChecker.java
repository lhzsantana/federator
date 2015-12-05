package rendezvous.federator.executor.consitency;

import rendezvous.federator.core.Value;

public interface ConsistencyChecker {

	public void checkValues(Value value1, Value value2);
	
}
