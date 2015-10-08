package rendezvous.federator;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import junit.framework.TestCase;
import rendezvous.federator.api.endpoint.impl.InsertEndpoint;

public class InsertEndpointTest extends TestCase {

	@Test
	public void testInsert() throws JsonParseException, JsonMappingException, IOException, ParseException {

		InsertEndpoint endpoint = new InsertEndpoint();
					
		endpoint.insert("{\"username\":\"luiz\"}");
	}
}
