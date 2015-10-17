package rendezvous.federator.planner.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import rendezvous.federator.core.Access;
import rendezvous.federator.core.Action;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.planner.Planner;

public class PlannerImpl implements Planner {

	final static Logger logger = Logger.getLogger(PlannerImpl.class);
	
	@Override
	public Plan createPlan(Action action, String entity, Set<Value> values) {

		Plan plan = new Plan();

		List<Access> accesses = new ArrayList<Access>();
		
		Map<DataSource,Set<Value>> reorderedValues = reorder(values);

		for(DataSource dataSource:reorderedValues.keySet()){

			Access access = new Access();
			access.setAction(action);
			access.setValues(reorderedValues.get(dataSource));
			access.setDataSource(dataSource);
			access.setEntity(new Entity(entity,null,null));
						
			accesses.add(access);
			
			for(Value value:access.getValues()){
				logger.info(
						"Added another access for the dataSource <"+dataSource.getName()+"> "
						+ "for entity <"+entity+"> "
						+ "in the field <"+value.getField()+"> "
						+ "and value <"+value.getValue()+"> "
						+ "and action <"+action+">"
				);
			}
		}

		plan.setAccesses(accesses);

		return plan;
	}
	
	private Map<DataSource,Set<Value>> reorder(Set<Value> values){
		
		Map<DataSource,Set<Value>> result = new HashMap<DataSource,Set<Value>>();
		
		for(Value value:values){
			
			logger.info("Value "+value.getValue()+" for the field "+value.getField());
			
			for(DataSource dataSource:value.getSources()){
				Set<Value> valuesForDataSource = result.get(dataSource);
				if(valuesForDataSource==null){
					valuesForDataSource=new HashSet<Value>();
				}
				valuesForDataSource.add(value);
				result.put(dataSource, valuesForDataSource);
			}
		}
		
		return result;
	}

	@Override
	public Plan createPlan(Action get, String entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
