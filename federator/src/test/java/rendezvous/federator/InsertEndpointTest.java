package rendezvous.federator;

import org.junit.Test;

import junit.framework.TestCase;
import rendezvous.federator.api.endpoint.impl.InsertEndpoint;

public class InsertEndpointTest extends TestCase {

	@Test
	public void testInsert() throws Exception {

		InsertEndpoint endpoint = new InsertEndpoint();
					
		endpoint.insert("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		//endpoint.insert("{'user':{'username':'luiz','password':'luiz','address':'luiz'}}");
	}
}
