package rendezvous.federator.planner.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.dictionary.Value;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Action;
import rendezvous.federator.planner.Plan;
import rendezvous.federator.planner.Planner;

public class PlannerImpl implements Planner {

	final static Logger logger = Logger.getLogger(PlannerImpl.class);

	public Plan createPlan(Action action, DataElement dataElement) {

		Plan plan = new Plan();

		List<Access> accesses = new ArrayList<Access>();

		for (Datasource dataSouce : dataElement.getDatasources()) {
			for (Value value : dataElement.getValues()) {
				logger.debug("Adding a new access");
				Access access = new Access();
				access.setDataElement(dataElement);
				access.setDatabase(dataSouce.getName());
				access.setAction(action);
				access.setField(value.getField());
				access.setValue(value.getValue());
	
				accesses.add(access);
			}
		}

		plan.setAccesses(accesses);

		return plan;
	}

	public Plan createPlan(Action action, Set<DataElement> dataElements) {

		Plan plan = new Plan();

		List<Access> accesses = new ArrayList<Access>();

		for (DataElement dataElement : dataElements) {
			for (Datasource dataSouce : dataElement.getDatasources()) {
				for (Value value : dataElement.getValues()) {
					
					Access access = new Access();
					access.setDataElement(dataElement);
					access.setDatabase(dataSouce.getName());
					access.setAction(action);
					access.setField(value.getField());
					access.setValue(value.getValue());

					logger.debug("Adding a new access"+access.toString());
					accesses.add(access);
				}
			}
		}

		plan.setAccesses(accesses);

		return plan;
	}

}
