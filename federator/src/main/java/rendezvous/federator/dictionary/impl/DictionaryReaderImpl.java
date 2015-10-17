package rendezvous.federator.dictionary.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.datasources.RelationshipManager;
import rendezvous.federator.datasources.column.cassandra.Cassandra;
import rendezvous.federator.datasources.document.mongodb.MongoDB;
import rendezvous.federator.dictionary.DictionaryReader;
import rendezvous.federator.dictionary.Rendezvous;

public class DictionaryReaderImpl implements DictionaryReader {

	private final static Logger logger = Logger.getLogger(DictionaryReaderImpl.class);

	private final static Map<Field, Set<DataSource>> dictionarySources = new HashMap<Field, Set<DataSource>>();
	private final static Map<Field, List<String>> dictionaryTypes = new HashMap<Field, List<String>>();

	@Override
	public List<String> getTypes(Field field) {
		return dictionaryTypes.get(field);
	}

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
				
				logger.debug("Adding the field <" + fieldName + "> of the entity field <" + entityName+ ">");
				
				Field field = new Field(fieldName, entityName);
				
				dictionarySources.put(field, dataSources);
				dictionaryTypes.put(field, types);
			}			
		}

		logger.info("Creating data elements");
		createDataElements();
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
			case "entity":
				source = new RelationshipManager();
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

		logger.info("Querying for the field "+field.getFieldName()+" from the entity "+field.getEntityName());
		
		return dictionarySources.get(field);
	}

	@Override
	public Set<Field> getFields() throws Exception{
		
		this.refreshDictionary();

		return dictionarySources.keySet();
	}
	
	private void createDataElements(){
		
		Map<String,Set<Field>> merged = mergeByEntity(dictionarySources.keySet());
		
		for(String entity:merged.keySet()){
			
			List<Field> fields = new ArrayList<Field>();
			fields.addAll(merged.get(entity));
			
			for(DataSource source : dictionarySources.get(fields.get(0))){
				source.createDataElements(new Entity(entity), merged.get(entity));
			}
		}
	}
	
	private Map<String,Set<Field>> mergeByEntity(Set<Field> fields){
		
		Map<String,Set<Field>> merger = new HashMap<String,Set<Field>>();
		
		for(Field field:fields){
			Set<Field> mergedFields = merger.get(field.getEntityName());
			
			if(mergedFields==null) mergedFields = new HashSet<Field>();
			mergedFields.add(field);
			
			merger.put(field.getEntityName(), mergedFields);
		}		
		
		return merger;
	}
}
