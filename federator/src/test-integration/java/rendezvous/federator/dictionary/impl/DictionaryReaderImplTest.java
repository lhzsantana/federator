package rendezvous.federator.dictionary.impl;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.dictionary.DictionaryReader;

public class DictionaryReaderImplTest {

	private final static Logger logger = Logger.getLogger(DictionaryReaderImplTest.class);
	
	@Test
	public void test() throws Exception {
		
		FileInputStream inputStream = new FileInputStream("mappings4.yml");
		String mapping = IOUtils.toString(inputStream);

		Long start = System.currentTimeMillis();
		
		DictionaryReader reader = new DictionaryReaderImpl();
		Map<Entity, Map<Datasource, Set<Field>>> map = reader.refreshDictionary(mapping);

		 logger.info("------");
		 
		for(Entity entity: map.keySet()){
			 Map<Datasource, Set<Field>> datasources = map.get(entity);
			 
			 logger.info("Entity "+entity.getName());
			 
			 for(Datasource datasource : datasources.keySet()){

				 logger.info("Datasource "+datasource.getName());
				 
				 Set<Field> fields = datasources.get(datasource);
				 
				 for(Field field:fields){
					 logger.info("Field "+field.getFieldName());					 
				 }				 
			 }			 
		}
		logger.info("------");

		Long total = System.currentTimeMillis()-start;
		
		logger.info("The test took <"+total+" ms>");
		
	}

}
