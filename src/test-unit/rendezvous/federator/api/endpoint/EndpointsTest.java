package rendezvous.federator.api.endpoint;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.api.endpoint.impl.MappingEndpoint;

public class EndpointsTest {

	private final static Logger logger = Logger.getLogger(EndpointsTest.class);

	@Test
	public void doNothing() throws JsonParseException, IOException, Exception {

		MappingEndpoint endpoint = Mockito.mock(MappingEndpoint.class);
		
		String json="{}";
		
		endpoint.extractElements(json);
		endpoint.extractEntities(json);
		endpoint.extractEntity(json);
		endpoint.extractValues(json);
		
		logger.info("Do nothing");
	}

}
