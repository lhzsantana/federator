package rendezvous.federator;

import java.io.IOException;

import junit.framework.TestCase;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import rendezvous.federator.api.endpoint.impl.InsertEndpoint;

public class InsertEndpointTest extends TestCase {

	@Test
	public void testInsert() throws IOException, ParseException {

		InsertEndpoint endpoint = new InsertEndpoint();
					
		endpoint.insert("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
	}
}
