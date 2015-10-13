package rendezvous.federator.api.endpoint.impl;

import org.apache.log4j.Logger;

import rendezvous.federator.api.GetResponse;
import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.planner.Action;
import rendezvous.federator.planner.Plan;

public class GetEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(GetEndpoint.class);
	
	public GetResponse get(String id) throws Exception {
				
		//find the dataSources that have to be queries to this call
		 DataElement dataElement = dictionary.getEntityById(id);
		
		 //create plan
		Plan plan = super.planner.createPlan(Action.GET, dataElement);
		
		//execute plan
		super.executor.connectToSources(dictionary.getDatasources());
		return super.executor.getExecute(plan);
	}
}
