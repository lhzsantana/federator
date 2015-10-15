package rendezvous.federator.api.endpoint.impl;

import org.apache.log4j.Logger;

import rendezvous.federator.api.GetResponse;
import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.planner.Action;
import rendezvous.federator.planner.Plan;

public class GetEndpoint extends Endpoint {

	final static Logger logger = Logger.getLogger(GetEndpoint.class);

	public GetResponse get(String id) throws Exception {

		String entity = "";//dictionary.getEntityById(id);

		Plan plan = super.planner.createPlan(Action.GET, entity);

		return super.executor.getExecute(plan);
	}
}
