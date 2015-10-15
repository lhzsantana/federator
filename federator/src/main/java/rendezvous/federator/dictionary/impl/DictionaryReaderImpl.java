package rendezvous.federator.dictionary.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import rendezvous.federator.core.Field;
import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.datasources.column.cassandra.Cassandra;
import rendezvous.federator.datasources.document.mongodb.MongoDB;
import rendezvous.federator.dictionary.DictionaryReader;
import rendezvous.federator.dictionary.Rendezvous;

public class DictionaryReaderImpl implements DictionaryReader {

	private final static Logger logger = Logger.getLogger(DictionaryReaderImpl.class);

	private final static Map<Field, Set<DataSource>> dictionary = new HashMap<Field, Set<DataSource>>();

	private void refreshDictionary() throws Exception {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

		Rendezvous rendezvous = mapper.readValue(new File("rendezvouz.yml"), Rendezvous.class);

		for (String entityName : rendezvous.getEntities().keySet()) {
			
			logger.debug("The entity <" + entityName + "> was found in the dictionary");
			
			for (String fieldName : rendezvous.getEntities().get(entityName).keySet()) {
					
				logger.debug("The field <" + fieldName + "> was found in the dictionary for entity <" + entityName + ">");
			
				List<String> types = rendezvous.getEntities().get(entityName).get(fieldName).get("type");
					
				for(String type:types){
					logger.debug("The type <" + type + "> was found in the dictionary for entity <" + entityName + ">");
				}
				
				List<String> sources = rendezvous.getEntities().get(entityName).get(fieldName).get("source");
				
				Set<DataSource> dataSources = new HashSet<DataSource>();
				for(String source:sources){
					logger.debug("The source <" + source + "> was found in the dictionary for the field <"+fieldName+"> of the entity <" + entityName + ">");
					dataSources.add(this.getDataSource(source));				
				}
				
				dictionary.put(new Field(fieldName, entityName), dataSources);
			}			
		}
	}
	
	private DataSource getDataSource(String sourceName) throws Exception{

		DataSource source = null;
		
		switch (sourceName) {
		
			case "mongodb":
				source = new MongoDB();
				break;
			case "cassandra":
				source = new Cassandra();
				break;	
			default:
				throw new Exception("The datasource "+sourceName+" is invalid");
		}
		
		source.setName(sourceName);
		
		return source;
	}
	
	@Override
	public Set<DataSource> getDatasources(Field field) throws JsonParseException, IOException, Exception {
		
		this.refreshDictionary();
	
		return dictionary.get(field);
	}

	@Override
	public Set<Field> getFields() throws Exception{
		
		this.refreshDictionary();

		return dictionary.keySet();
	}
}
