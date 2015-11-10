package rendezvous.federator.api.endpoint.impl;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.core.Action;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;

@Path("/_query")
public class QueryEndpoint extends Endpoint{

	final static Logger logger = Logger.getLogger(QueryEndpoint.class);

	@GET
	@Path("/{json}")
	public Response query(@PathParam("json") String json) throws JsonParseException, IOException, Exception {

		String extractedEntity = super.extractEntity(json);
		
		Set<Value> extractedValues = super.extractValues(json);
		
		Plan plan = super.planner.createPlan(Action.QUERY, new Entity(extractedEntity), extractedValues);

		return Response.status(200).entity(super.executor.queryExecute(plan).toString()).build();	
	}
}
