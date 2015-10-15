package rendezvous.federator.api.endpoint.impl;

import org.junit.Test;

import static org.junit.Assert.*;

import rendezvous.federator.api.GetResponse;
import rendezvous.federator.api.InsertResponse;
import rendezvous.federator.api.Response;
import rendezvous.federator.api.endpoint.impl.DeleteEndpoint;
import rendezvous.federator.api.endpoint.impl.GetEndpoint;
import rendezvous.federator.api.endpoint.impl.InsertEndpoint;

public class GetEndpointTest {

	@Test
	public void testGetUnexistent() throws Exception {

		DeleteEndpoint deleteEndpoint = new DeleteEndpoint();
		Response response = deleteEndpoint.delete("12345");
		
		assertNotNull(response.getHits());
		
		GetEndpoint getEndpoint = new GetEndpoint();
		GetResponse getResponse = getEndpoint.get("12345");

		assertNotNull(getResponse.getHits());
	}

	@Test
	public void test() throws Exception {

		InsertEndpoint insertEndpoint = new InsertEndpoint();
		InsertResponse response1 = insertEndpoint.insert("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		
		GetEndpoint getEndpoint = new GetEndpoint();
		GetResponse response = getEndpoint.get(response1.getId());

		assertNotNull(response.getHits());
	}	
}
