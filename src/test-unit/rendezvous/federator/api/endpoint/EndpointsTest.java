package rendezvous.federator.api.endpoint;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;

public class EndpointsTest {

	private final static Logger logger = Logger.getLogger(EndpointsTest.class);

	@Test
	public void doNothing() throws JsonParseException, IOException, Exception {

		logger.info("Do nothing");
	}

}
