package rendezvous.federator.api.endpoint;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.api.endpoint.impl.InsertEndpoint;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.dictionary.impl.DictionaryReaderImpl;

public class EndpointTest {

	private final static Logger logger = Logger.getLogger(EndpointTest.class);
	
	@Test
	public void testExtractValues() throws JsonParseException, IOException, Exception {
				
		Endpoint endpoint = new InsertEndpoint();
		
		Set<Value> extractedValues = endpoint.extractValues("{\"user\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		
		for(Value value : extractedValues){
			for(DataSource source : value.getSources()){
				logger.debug("The extracted value have the field <"+value.getField()+">, the value <"+value.getValue()+"> and the source <"+source.getName()+">");
			}
		}
	}
}
