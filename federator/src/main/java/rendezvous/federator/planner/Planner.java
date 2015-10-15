package rendezvous.federator.planner;

import java.util.Set;

import rendezvous.federator.dictionary.Value;

public interface Planner {

	public Plan createPlan(Action action, String entity, Set<Value> values);

	public Plan createPlan(Action get, String entity);

}
