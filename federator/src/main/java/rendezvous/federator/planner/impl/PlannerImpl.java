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
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.dictionary.DictionaryReader;
import rendezvous.federator.dictionary.impl.DictionaryReaderImpl;
import rendezvous.federator.planner.Planner;

public class PlannerImpl implements Planner {

	final static Logger logger = Logger.getLogger(PlannerImpl.class);
	
	@Override
	public Plan createPlan(Action action, Entity entity, Set<Value> values) {

		Plan plan = new Plan();

		List<Access> accesses = new ArrayList<Access>();
		
		Map<Datasource,Set<Value>> reorderedValues = reorder(values);

		for(Datasource dataSource:reorderedValues.keySet()){

			Access access = new Access();
			access.setAction(action);
			access.setValues(reorderedValues.get(dataSource));
			access.setDataSource(dataSource);
			
			entity.setMappingHash(DictionaryReaderImpl.getLastMapping());
			
			access.setEntity(entity);
						
			accesses.add(access);
			
			for(Value value:access.getValues()){
				logger.info(
						"Added another access for the dataSource <"+dataSource.getName()+"> "
						+ "for entity <"+entity.getName()+"> "
						+ "in the field <"+value.getField()+"> "
						+ "and value <"+value.getValue()+"> "
						+ "and action <"+action+">"
				);
			}
		}

		plan.setAccesses(accesses);

		return plan;
	}
	
	private Map<Datasource,Set<Value>> reorder(Set<Value> values){
		
		Map<Datasource,Set<Value>> result = new HashMap<Datasource,Set<Value>>();
		
		for(Value value:values){
			
			logger.debug("Value "+value.getValue()+" for the field "+value.getField());
			
			for(Datasource dataSource:value.getSources()){
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
	
}
