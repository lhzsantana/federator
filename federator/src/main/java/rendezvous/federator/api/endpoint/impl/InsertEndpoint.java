package rendezvous.federator.api.endpoint.impl;

import java.util.Set;

import org.apache.log4j.Logger;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.api.response.InsertResponse;
import rendezvous.federator.core.Action;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;

public class InsertEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(InsertEndpoint.class);
	
	public InsertResponse insert(String json) throws Exception {

		String extractedEntity = super.extractEntity(json);
		
		Set<Value> extractedValues = super.extractValues(json);

		Plan plan = super.planner.createPlan(Action.INSERT, new Entity(extractedEntity), extractedValues);
		
		return super.executor.insertExecute(plan);
	}
}
