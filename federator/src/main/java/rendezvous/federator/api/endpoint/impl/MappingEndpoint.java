package rendezvous.federator.api.endpoint.impl;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.dictionary.impl.DictionaryReaderImpl;

@Path("/_mapping")
public class MappingEndpoint extends Endpoint{

	final static Logger logger = Logger.getLogger(MappingEndpoint.class);

	@PUT
	public void put(String json) throws Exception {
		
		DictionaryReaderImpl reader = new DictionaryReaderImpl();
		
		reader.refreshDictionary(json);

		logger.info("A new mapping was added");
	}
}
