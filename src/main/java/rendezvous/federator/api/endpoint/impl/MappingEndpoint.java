package rendezvous.federator.api.endpoint.impl;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.dictionary.impl.DataCreator;
import rendezvous.federator.dictionary.impl.DictionaryReaderImpl;

@Path("/_mapping")
public class MappingEndpoint extends Endpoint{

	final static Logger logger = Logger.getLogger(MappingEndpoint.class);

	@PUT
	public void put(String json) throws Exception {
		
		DictionaryReaderImpl reader = new DictionaryReaderImpl();
		
		Map<Entity, Map<Datasource, Set<Field>>> dictionaryEntitySourceFields = reader.refreshDictionary(json);
		
		DataCreator dataCreator = new DataCreator();
		dataCreator.createDataElements(dictionaryEntitySourceFields);

		logger.info("A new mapping was added");
	}
}
