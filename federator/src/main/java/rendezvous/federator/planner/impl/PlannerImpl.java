package rendezvous.federator.planner.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.canonicalModel.DataSource;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;
import rendezvous.federator.planner.Planner;

public class PlannerImpl implements Planner{

	final static Logger logger = Logger.getLogger(PlannerImpl.class);

	public Plan createPlan(Set<DataElement> dataElements) {
		
		Plan plan = new Plan();
		
		List<Access> accesses = new ArrayList<Access>();
		
		for(DataElement dataElement:dataElements){
			for(DataSource dataSouce : dataElement.getDataSources()){
				Access access=new Access();
				access.setDataElement(dataElement);
				access.setDatabase(dataSouce.getName());
				access.setQuery("def");
				
				accesses.add(access);
			}
		}
		
		plan.setAccesses(accesses);;

		return plan;
	}
	
}
