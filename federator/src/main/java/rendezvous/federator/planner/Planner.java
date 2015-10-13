package rendezvous.federator.planner;

import java.util.Set;

import rendezvous.federator.canonicalModel.DataElement;

public interface Planner {

	public Plan createPlan(Action action, Set<DataElement> dataElements);
	
	public Plan createPlan(Action action, DataElement dataElements);

}
