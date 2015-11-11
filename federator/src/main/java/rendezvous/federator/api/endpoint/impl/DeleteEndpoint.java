package rendezvous.federator.api.endpoint.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.api.response.DeleteResponse;
import rendezvous.federator.entityManager.EntityManager;

@Path("/_delete")
public class DeleteEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(DeleteEndpoint.class);

	@GET
	@Path("/{id}")
	public Response delete(@PathParam("id") String id) throws Exception {
		
		EntityManager.deleteEntity(id);
		
		DeleteResponse response = new DeleteResponse();
		
		return response;
	}

}
