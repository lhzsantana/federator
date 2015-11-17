package rendezvous.federator.api.endpoint.impl;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.api.response.GetResponse;
import rendezvous.federator.core.Access;
import rendezvous.federator.core.Plan;
import rendezvous.federator.entityManager.EntityManager;

@Path("/_get")
public class GetEndpoint extends Endpoint {

	final static Logger logger = Logger.getLogger(GetEndpoint.class);

	@GET
	@Path("/{id}")
	public GetResponse get(@PathParam("id") String id) throws Exception {
		
		logger.info("Getting the entity <"+id+">");
		
		Plan plan = new Plan();
		
		List<Access> access = EntityManager.getEntity(id);
		
		GetResponse response = new GetResponse();
		
		if(access!=null && !access.isEmpty()){
		
			plan.setAccesses(access);
		
			return super.executor.getExecute(plan);
		}

		return new GetResponse();
	}
}
