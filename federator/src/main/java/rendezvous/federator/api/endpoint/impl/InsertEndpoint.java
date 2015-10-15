package rendezvous.federator.api.endpoint.impl;

import java.util.Set;

import org.apache.log4j.Logger;

import rendezvous.federator.api.InsertResponse;
import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.dictionary.Value;
import rendezvous.federator.planner.Action;
import rendezvous.federator.planner.Plan;

public class InsertEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(InsertEndpoint.class);
	
	public InsertResponse insert(String json) throws Exception {

		String extractEntity = super.extractEntity(json);
		Set<Value> extractedValues = super.extractValues(json);

		Plan plan = super.planner.createPlan(Action.INSERT, extractEntity, extractedValues);
		
		return super.executor.insertExecute(plan);
	}
}
