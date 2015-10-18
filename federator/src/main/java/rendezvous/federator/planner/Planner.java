package rendezvous.federator.planner;

import java.util.Set;

import rendezvous.federator.core.Action;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;

public interface Planner {

	public Plan createPlan(Action action, Entity entity, Set<Value> values);

}
