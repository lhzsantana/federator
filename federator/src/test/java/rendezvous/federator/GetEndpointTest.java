package rendezvous.federator;

import org.junit.Test;

import rendezvous.federator.api.endpoint.impl.GetEndpoint;

public class GetEndpointTest {

	@Test
	public void test() throws Exception {
		GetEndpoint endpoint = new GetEndpoint();
		endpoint.get("");
	}

}
