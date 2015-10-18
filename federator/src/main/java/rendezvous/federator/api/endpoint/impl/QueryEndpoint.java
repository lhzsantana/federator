package rendezvous.federator.api.endpoint.impl;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.api.response.QueryResponse;
import rendezvous.federator.core.Action;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;

public class QueryEndpoint extends Endpoint{

	final static Logger logger = Logger.getLogger(QueryEndpoint.class);
	
	public QueryResponse query(String json) throws JsonParseException, IOException, Exception {

		String extractedEntity = super.extractEntity(json);
		
		Set<Value> extractedValues = super.extractValues(json);
		
		Plan plan = super.planner.createPlan(Action.QUERY, new Entity(extractedEntity), extractedValues);

		return super.executor.queryExecute(plan);		
	}
}
