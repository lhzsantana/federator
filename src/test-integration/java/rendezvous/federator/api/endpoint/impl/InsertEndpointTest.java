package rendezvous.federator.api.endpoint.impl;

import org.junit.Test;

import junit.framework.TestCase;
import rendezvous.federator.api.endpoint.EndpointsTest;
import rendezvous.federator.api.endpoint.InsertParam;
import rendezvous.federator.api.endpoint.impl.InsertEndpoint;
import rendezvous.federator.api.response.impl.Response;

public class InsertEndpointTest extends EndpointsTest {

	@Test
	public void testInsert() throws Exception {

		InsertParam param = new InsertParam();
		
		param.entity="{\"usuarios\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}";
		InsertEndpoint endpoint = new InsertEndpoint();
		Response response = endpoint.insert(param);
	}

	@Test
	public void simpleInsert() throws Exception {

		InsertEndpoint endpoint = new InsertEndpoint();
		//Response response1 = endpoint.insert("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		//Response response2 = endpoint.insert("{\"user\":{\"username\":\"eduardo\",\"password\":\"eduardo\",\"address\":\"luiz\"}}");
		//Response response3 = endpoint.insert("{\"user\":{\"name\":\"bruna\"}}");
	}

}
