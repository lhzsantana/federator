package rendezvous.federator.api.endpoint.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import rendezvous.federator.api.InsertResponse;
import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.planner.Action;
import rendezvous.federator.planner.Plan;

public class InsertEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(InsertEndpoint.class);
	
	public InsertResponse insert(String string) throws Exception {
		
		super.isJSONValid(string);

		//find the data entities
		JSONObject entities = super.extractEntities(string);
						
		//find the dataElments related with each new insertion
		Set<DataElement> dataElements = new HashSet<DataElement>();
		
		for(Object entityObject : entities.keySet()){
			
			String entity = entityObject.toString();
			String values  = entities.get(entity).toString();
			
			logger.debug("The element " + entity + " will be inserted with the values " + values);
			
			DataElement dataElement =dictionary.getDataElement(entity);
			dataElement.setValues(super.extractValues(values));
			dataElement.setEntity(entity);
			
			dataElements.add(dataElement);
		}

		//create plan
		Plan plan = super.planner.createPlan(Action.INSERT, dataElements);
		
		//execute plan
		super.executor.connectToSources(dictionary.getDatasources());;
		return super.executor.insertExecute(plan);
	}
}
