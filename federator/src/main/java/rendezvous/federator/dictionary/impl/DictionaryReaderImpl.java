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

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.datasources.RelationshipManager;
import rendezvous.federator.datasources.column.DatasourceColumn;
import rendezvous.federator.datasources.column.cassandra.Cassandra;
import rendezvous.federator.datasources.document.DatasourceDocument;
import rendezvous.federator.datasources.document.mongodb.MongoDB;
import rendezvous.federator.datasources.graph.neo4j.Neo4J;
import rendezvous.federator.datasources.keyvalue.redis.Redis;
import rendezvous.federator.dictionary.DictionaryManager;
import rendezvous.federator.dictionary.DictionaryReader;
import rendezvous.federator.dictionary.Mapping;

public class DictionaryReaderImpl implements DictionaryReader {

	private final static Logger logger = Logger.getLogger(DictionaryReaderImpl.class);
	private final static DictionaryManager manager = new DictionaryManager();

	private final static Map<Field, Set<Datasource>> dictionarySources = new HashMap<Field, Set<Datasource>>();
	private final static Map<Field, List<String>> dictionaryTypes = new HashMap<Field, List<String>>();
	private final static Map<Entity, Set<Datasource>> dictionaryEntities = new HashMap<Entity, Set<Datasource>>();
	
	private final static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());	

	private String path=null;

	public DictionaryReaderImpl(){
		this.path="mapping.yml";
	}	
	
	public DictionaryReaderImpl(String path){
		this.path=path;
	}	
	
	@Override
	public List<String> getTypes(Field field) {
		return dictionaryTypes.get(field);
	}

	private void refreshDictionary() throws Exception {
		
		Mapping mapping = mapper.readValue(new File(path), Mapping.class);
		
		refreshDictionary(mapping);
	}

	@Override
	public void refreshDictionary(String newMapping) throws Exception {

		Mapping mapping = mapper.readValue(newMapping, Mapping.class);
		
		refreshDictionary(mapping);
		
	}

	private Set<Datasource> getSources(Mapping mapping, Entity entity, Field field) throws Exception{

		List<String> sources = mapping.getEntities().get(entity.getName()).get(field.getFieldName()).get("source");
		
		Set<Datasource> dataSources = new HashSet<Datasource>();
		for(String source:sources){
			logger.info("The source <" + source + "> was found in the dictionary for the field <"+field.getFieldName()+"> of the entity <" + entity.getName() + ">");
			dataSources.add(this.getDataSource(source));				
		}
		
		return dataSources;
	}
	
	private Set<Field> getFields(Mapping mapping, Entity entity) throws Exception{

		Set<Field> fields= new HashSet<Field>();
		
		for (String fieldName : mapping.getEntities().get(entity.getName()).keySet()) {

			Field field = new Field(fieldName, entity);
			fields.add(field);
			
			logger.debug("The field <" + fieldName + "> was found in the dictionary for entity <" + entity.getName() + ">");
		
			List<String> types = mapping.getEntities().get(entity.getName()).get(field.getFieldName()).get("type");
				
			for(String type:types){
				logger.debug("The type <" + type + "> was found in the dictionary for entity <" + entity.getName() + ">");
			}
			
			logger.debug("Adding the field <" + fieldName + "> of the entity field <" + entity.getName()+ ">");
			
			Set<Datasource> datasources = getSources(mapping, entity, field);
			
			dictionarySources.put(field, datasources);
			dictionaryEntities.put(entity, datasources);
			dictionaryTypes.put(field, types);
		}
		
		return fields;
	}
	
	private void refreshDictionary(Mapping mapping) throws Exception {

		Integer mappingHash = mapping.hashCode();
		
		if(!manager.containsMapping(mappingHash)){
			
			for (String entityName : mapping.getEntities().keySet()) {
				
				logger.debug("The entity <" + entityName + "> was found in the dictionary");
				
				Entity entity = new Entity(entityName);
				
				entity.setMappingHash(mappingHash);
								
				entity.setFields(getFields(mapping, entity));
			}
			
			manager.addMapping(mapping);
		}
		
		logger.info("Creating data elements");
		
		createDataElements();
	}
	
	private Datasource getDataSource(String sourceName) throws Exception{

		Datasource source = null;
		
		switch (sourceName) {
		
			case "mongodb":
				source = new MongoDB();
				break;
			case "cassandra":
				source = new Cassandra();
				break;
			case "neo4j":
				source = new Neo4J();
				break;
			case "redis":
				source = new Redis();
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
	public Set<Datasource> getDatasources(Field field) throws JsonParseException, IOException, Exception {
		
		logger.info("Querying for the field "+field.getFieldName()+" from the entity "+field.getEntity().getName());
		
		return dictionarySources.get(field);
	}

	@Override
	public Set<Field> getFields() throws Exception{
		
		this.refreshDictionary();

		return dictionarySources.keySet();
	}
	
	private void createDataElements() throws InvalidDatasource{

		Map<String,Set<Field>> merged = mergeByEntity(dictionarySources.keySet());
		
		for(Entity entity:dictionaryEntities.keySet()){
			
			logger.info("Entity "+entity.getName());

			for(Datasource source : dictionaryEntities.get(entity)){

				logger.info("Checking the source <"+source.getName()+"> for the entity <"+entity.getName()+">");
								
				if (source instanceof DatasourceColumn) {
					((DatasourceColumn)source).createDataElements(entity, merged.get(entity));
				}else if (source instanceof DatasourceDocument) {
					((DatasourceDocument)source).createDataElements(entity, merged.get(entity));					
				}else if (source instanceof RelationshipManager) {
					logger.warn("Relationship not implemented yet");				
				}else{
					throw new InvalidDatasource();
				}
			}
		}
	}
	
	private Map<String,Set<Field>> mergeByEntity(Set<Field> fields){
		
		Map<String,Set<Field>> merger = new HashMap<String,Set<Field>>();
		
		for(Field field:fields){
			
			Set<Field> mergedFields = merger.get(field.getEntity().getName());
			
			if(mergedFields==null) mergedFields = new HashSet<Field>();
			mergedFields.add(field);
			
			merger.put(field.getEntity().getName(), mergedFields);
		}		
		
		return merger;
	}
}
