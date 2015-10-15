package rendezvous.federator.dictionary;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.dictionary.impl.DictionaryReaderImpl;
import rendezvous.federator.dictionary.impl.Field;

public class DictionaryReaderImplTest {

	private final static Logger logger = Logger.getLogger(DictionaryReaderImplTest.class);

	@Test
	public void testGetDatasources() throws JsonParseException, IOException, Exception {
		DictionaryReader reader = new DictionaryReaderImpl();

		for (Field field : reader.getFields()) {
			
			for (DataSource source : reader.getDatasources(field)) {
				logger.debug("The datasource <"+source.getName()+"> for the field <"+field.getFieldName()+"> was found in the entity <"+field.getEntityName()+">");
			}
		}
	}
}
