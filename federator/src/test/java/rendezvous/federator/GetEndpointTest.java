package rendezvous.federator;

import org.junit.Test;

import rendezvous.federator.api.GetResponse;
import rendezvous.federator.api.InsertResponse;
import rendezvous.federator.api.endpoint.impl.GetEndpoint;
import rendezvous.federator.api.endpoint.impl.InsertEndpoint;

public class GetEndpointTest {

	@Test
	public void test() throws Exception {

		InsertEndpoint insertEndpoint = new InsertEndpoint();
		InsertResponse response1 = insertEndpoint.insert("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		
		GetEndpoint getEndpoint = new GetEndpoint();
		GetResponse response = getEndpoint.get(response1.getId());
	}
	
}
