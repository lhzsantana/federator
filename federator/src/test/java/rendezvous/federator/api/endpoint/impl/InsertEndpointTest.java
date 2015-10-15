package rendezvous.federator.api.endpoint.impl;

import org.junit.Test;

import junit.framework.TestCase;
import rendezvous.federator.api.Response;
import rendezvous.federator.api.endpoint.impl.InsertEndpoint;

public class InsertEndpointTest extends TestCase {

	@Test
	public void testInsert() throws Exception {

		InsertEndpoint endpoint = new InsertEndpoint();
		Response response = endpoint.insert("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
	}

	@Test
	public void simpleInsert() throws Exception {

		InsertEndpoint endpoint = new InsertEndpoint();
		//Response response1 = endpoint.insert("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		//Response response2 = endpoint.insert("{\"user\":{\"username\":\"eduardo\",\"password\":\"eduardo\",\"address\":\"luiz\"}}");
		//Response response3 = endpoint.insert("{\"user\":{\"name\":\"bruna\"}}");
	}

}