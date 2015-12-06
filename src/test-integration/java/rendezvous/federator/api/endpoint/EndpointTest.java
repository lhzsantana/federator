package rendezvous.federator.api.endpoint;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.api.endpoint.impl.InsertEndpoint;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.Datasource;

public class EndpointTest {

	private final static Logger logger = Logger.getLogger(EndpointsTest.class);

	@Test
	public void testExtractValues() throws JsonParseException, IOException, Exception {

		Endpoint endpoint = new InsertEndpoint();

		Set<Value> extractedValues = endpoint
				.extractValues("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");

		for (Value value : extractedValues) {
			for (Datasource source : value.getSources()) {
				logger.debug("The extracted value have the field <" + value.getField() + ">, the value <"
						+ value.getValue() + "> and the source <" + source.getName() + ">");
			}
		}
	}

	protected void printHits(List<Hit> hits) {

		for (Hit hit : hits) {

			logger.info("-------------------------");

			for (Value value : hit.getValues()) {
				logger.info("The value <" + value.getValue() + "> was found for field <" + value.getField()
						+ "> of the entity <" + value.getEntity() + ">");
			}

			logger.info("-------------------------");

			logger.info(hit.toString());

			logger.info("-------------------------");
			logger.info("-------------------------");

		}
	}
}
