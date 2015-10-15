package rendezvous.federator.api.endpoint.impl;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;

import rendezvous.federator.api.GetResponse;
import rendezvous.federator.api.InsertResponse;
import rendezvous.federator.api.Response;

public class GetEndpointTest {
	
	final static Logger logger = Logger.getLogger(GetEndpointTest.class);

	@Test
	public void testGetUnexistent() throws Exception {

		DeleteEndpoint deleteEndpoint = new DeleteEndpoint();
		Response response = deleteEndpoint.delete("12345");
				
		GetEndpoint getEndpoint = new GetEndpoint();
		GetResponse getResponse = getEndpoint.get("12345");

		assertNotNull(getResponse.getHits());
	}

	@Test
	public void test() throws Exception {

		InsertEndpoint insertEndpoint = new InsertEndpoint();
		InsertResponse insertResponse = insertEndpoint.insert("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		
		logger.debug("The insert response has the id <"+insertResponse.getId()+">");
		
		GetEndpoint getEndpoint = new GetEndpoint();
		GetResponse response = getEndpoint.get(insertResponse.getId());
		
		assertNotNull(response.getHits());
	}	
}
