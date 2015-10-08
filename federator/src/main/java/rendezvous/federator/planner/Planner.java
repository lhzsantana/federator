package rendezvous.federator.planner;

import java.util.Set;

import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.canonicalModel.DataSource;

public interface Planner {

	public Plan createPlan(Set<DataElement> dataElements, Set<DataSource> dataSources);

}
