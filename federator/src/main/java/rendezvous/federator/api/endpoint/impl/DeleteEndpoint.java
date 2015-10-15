package rendezvous.federator.api.endpoint.impl;

import org.apache.log4j.Logger;

import rendezvous.federator.api.Response;
import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.entityManager.EntityManager;

public class DeleteEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(DeleteEndpoint.class);
	
	public Response delete(String id) throws Exception {
		
		EntityManager.deleteEntity(id);
		
		return null;
	}

}
