package rendezvous.federator.planner;

import java.util.Set;

import rendezvous.federator.canonicalModel.DataElement;

public interface Planner {

	public Plan createPlan(Set<DataElement> dataElements);

}
