/* This class sole responsibility is to create the insert endpoint
*/

package rendezvous.federator.api.endpoint.impl;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.api.endpoint.InsertParam;
import rendezvous.federator.api.response.InsertResponse;
import rendezvous.federator.core.Action;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;

@Path("/federator")
public class InsertEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(InsertEndpoint.class);

	@POST
	@Consumes("application/json")
	@Path("/_insert")
	public InsertResponse insert2(final InsertParam param) throws Exception {

		logger.info("Inserting "+param);
		
		String extractedEntity = super.extractEntity(param.toString());
				
		Set<Value> extractedValues = super.extractValues(param.toString());

		Plan plan = super.planner.createPlan(Action.INSERT, new Entity(extractedEntity), extractedValues);
		
		return super.executor.insertExecute(plan);
	}
	
	
	@POST
	@Consumes("application/json")
	public InsertResponse insert(final InsertParam param) throws Exception {

		logger.info("Inserting "+param.entity);
		
		String extractedEntity = super.extractEntity(param.entity);
				
		Set<Value> extractedValues = super.extractValues(param.entity);

		Plan plan = super.planner.createPlan(Action.INSERT, new Entity(extractedEntity), extractedValues);
		
		return super.executor.insertExecute(plan);
	}
}
