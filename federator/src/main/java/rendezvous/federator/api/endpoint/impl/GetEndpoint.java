package rendezvous.federator.api.endpoint.impl;

import java.util.List;

import org.apache.log4j.Logger;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.api.response.GetResponse;
import rendezvous.federator.core.Access;
import rendezvous.federator.core.Plan;
import rendezvous.federator.entityManager.EntityManager;

public class GetEndpoint extends Endpoint {

	final static Logger logger = Logger.getLogger(GetEndpoint.class);

	public GetResponse get(String id) throws Exception {
		
		Plan plan = new Plan();
		
		List<Access> access = EntityManager.getEntity(id);
		
		GetResponse getResponse = new GetResponse();
		
		if(access!=null && !access.isEmpty()){
		
			plan.setAccesses(access);
		
			return super.executor.getExecute(plan);
		}
		
		return getResponse;
	}
}
