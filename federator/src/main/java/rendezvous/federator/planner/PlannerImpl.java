package rendezvous.federator.planner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.canonicalModel.DataSource;

public class PlannerImpl implements Planner{

	public Plan createPlan(Set<DataElement> dataElements, Set<DataSource> dataSources) {
		
		Plan plan = new Plan();
		
		List<Access> accesses = new ArrayList<Access>();
		
		for(DataElement dataElement:dataElements){
			Access access=new Access();
			access.setDatabase("");
			access.setQuery("");
			
			accesses.add(access);
		}
		
		plan.setAccesses(accesses);;

		return plan;
	}
	
}
