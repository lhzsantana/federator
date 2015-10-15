package rendezvous.federator.planner;

import java.util.Set;

import rendezvous.federator.core.Action;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;

public interface Planner {

	public Plan createPlan(Action action, String entity, Set<Value> values);

	public Plan createPlan(Action get, String entity);

}
