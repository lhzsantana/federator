package rendezvous.federator.api.endpoint.impl;

import org.apache.log4j.Logger;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.api.response.DeleteResponse;
import rendezvous.federator.api.response.impl.Status;
import rendezvous.federator.entityManager.EntityManager;

public class DeleteEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(DeleteEndpoint.class);
	
	public DeleteResponse delete(String id) throws Exception {
		
		EntityManager.deleteEntity(id);
		
		DeleteResponse response = new DeleteResponse();
		response.setStatus(Status.SUCCESS);
		
		return response;
	}

}
