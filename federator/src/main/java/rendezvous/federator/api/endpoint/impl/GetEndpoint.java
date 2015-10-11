package rendezvous.federator.api.endpoint.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import rendezvous.federator.api.Response;
import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.planner.Action;
import rendezvous.federator.planner.Plan;

public class GetEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(GetEndpoint.class);
	
	public Response get(String string) throws Exception {
		
		super.isJSONValid(string);

		//find the data elements
		JSONObject elements = super.extractElements(string);
		
		//find the dataElments related with each new insertion
		Set<DataElement> dataElements = new HashSet<DataElement>();
		
		for(Object element : elements.keySet()){
			logger.debug("The element " + element + " will be inserted");
			DataElement dataElement =dictionary.getDataElement(element.toString());
			dataElement.setValue(elements.get(element).toString());			
			dataElements.add(dataElement);
		}

		//create plan
		Plan plan = super.planner.createPlan(Action.INSERT, dataElements);
		
		//execute plan
		super.executor.connectToSources(dictionary.getDatasources());;
		return super.executor.execute(plan);
	}

}
