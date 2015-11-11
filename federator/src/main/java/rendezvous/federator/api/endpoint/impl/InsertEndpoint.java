package rendezvous.federator.api.endpoint.impl;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.api.response.InsertResponse;
import rendezvous.federator.core.Action;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;

@Path("/_insert")
public class InsertEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(InsertEndpoint.class);

	@GET
	@Path("/{json}")
	public InsertResponse insert(@PathParam("json") String json) throws Exception {

		String extractedEntity = super.extractEntity(json);
		
		Set<Value> extractedValues = super.extractValues(json);

		Plan plan = super.planner.createPlan(Action.INSERT, new Entity(extractedEntity), extractedValues);
		
		return super.executor.insertExecute(plan);
	}
}
