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

	private final static Map<Entity, Map<Datasource, Set<Field>>> dictionaryEntitySourceFields = new HashMap<Entity, Map<Datasource, Set<Field>>>();
	private final static Map<Field, Set<Datasource>> dictionarySources = new HashMap<Field, Set<Datasource>>();
	private final static Map<Datasource, Set<Field>> dictionaryFields = new HashMap<Datasource, Set<Field>>();
	private final static Map<Field, List<String>> dictionaryTypes = new HashMap<Field, List<String>>();
	private final static Map<Entity, Set<Datasource>> dictionaryEntities = new HashMap<Entity, Set<Datasource>>();
	private final static Map<Entity, Set<Field>> dictionaryEntitiesFields = new HashMap<Entity, Set<Field>>();
	
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
		
		refreshDictionary2(mapping);
		
	}

	
	private final static Map<Entity, Map<Datasource, Set<Field>>> dictionaryEntitySourceFields2 = new HashMap<Entity, Map<Datasource, Set<Field>>>();
	private final static Map<Datasource, Set<Field>> dictionaryDatasourceFields = new HashMap<Datasource, Set<Field>>();
	
	private void refreshDictionary2(Mapping mapping) throws Exception {
		
		Integer mappingHash = mapping.hashCode();
		
		if(!manager.containsMapping(mappingHash)){
			
			for (String entityName : mapping.getEntities().keySet()) {
				
				logger.debug("The entity <" + entityName + "> was found in the dictionary");
				
				Entity entity = new Entity(entityName);
				
				entity.setMappingHash(mappingHash);
				
				Set<Field> fields = new HashSet<Field>();
								
				for (String fieldName : mapping.getEntities().get(entity.getName()).keySet()) {					

					logger.debug("The field <" + fieldName + "> of entity <"+entityName+"> was found in the dictionary");
					
					Field field = new Field(fieldName, entity);
					fields.add(field);

					for(String datasourceName : mapping.getEntities().get(entity.getName()).get(field.getFieldName()).get("source")){

						logger.debug("The datasource <"+datasourceName+"> for field <" + fieldName + "> of entity <"+entityName+"> was found in the dictionary");
						
						Datasource datasource = this.getDatasource(datasourceName);
						
						addFieldForDatasource(datasource,field);
					}
				}
				
				entity.setFields(fields);
												
				dictionaryEntitySourceFields2.put(entity, dictionaryDatasourceFields);
			}
			
			manager.addMapping(mapping);
		}		
	}
	
	private void addFieldForDatasource(Datasource datasource, Field field){

		Set<Field> fields = dictionaryDatasourceFields.get(datasource);
		
		if(fields==null){
			fields = new HashSet<Field>();
		}
		
		fields.add(field);
	}
	
	
	
	
	
	
	
	
	
	
	

	public Set<Field> manageField(Field field){

		Set<Field> fields = dictionaryFields.get(field);
		
		if(fields==null){
			fields = new HashSet<Field>();
		}
		
		fields.add(field);
		
		return fields;
	}
	
	private Set<Datasource> getSources(Mapping mapping, Entity entity, Field field) throws Exception{

		List<String> sources = mapping.getEntities().get(entity.getName()).get(field.getFieldName()).get("source");
		
		Set<Datasource> dataSources = new HashSet<Datasource>();
		for(String source:sources){
			logger.info("The source <" + source + "> was found in the dictionary for the field <"+field.getFieldName()+"> of the entity <" + entity.getName() + ">");
			Datasource datasource = this.getDatasource(source);
			
			dataSources.add(datasource);

			dictionaryFields.put(datasource, manageField(field));
		}
		
		return dataSources;
	}
	
	private void getFields(Mapping mapping, Entity entity) throws Exception{

		Set<Datasource> allDatasources= new HashSet<Datasource>();
		
		for (String fieldName : mapping.getEntities().get(entity.getName()).keySet()) {

			Field field = new Field(fieldName, entity);
			
			logger.info("The field <" + fieldName + "> was found in the dictionary for entity <" + entity.getName() + ">");
		
			List<String> types = mapping.getEntities().get(entity.getName()).get(field.getFieldName()).get("type");
				
			for(String type:types){
				logger.info("The type <" + type + "> was found in the dictionary for entity <" + entity.getName() + ">");
			}
						
			Set<Datasource> datasources = getSources(mapping, entity, field);
			allDatasources.addAll(datasources);			
		}

		Map<Datasource, Set<Field>> dataSourcesField = new HashMap<Datasource, Set<Field>>();
		
		for(Datasource datasource:allDatasources){			
			dataSourcesField.put(datasource, dictionaryFields.get(datasource));
		}
		
		dictionaryEntitySourceFields.put(entity, dataSourcesField);
		
	}
	
	private void refreshDictionary(Mapping mapping) throws Exception {

		Integer mappingHash = mapping.hashCode();
		
		if(!manager.containsMapping(mappingHash)){
			
			for (String entityName : mapping.getEntities().keySet()) {
				
				logger.debug("The entity <" + entityName + "> was found in the dictionary");
				
				Entity entity = new Entity(entityName);
				
				entity.setMappingHash(mappingHash);
								
				getFields(mapping, entity);
			}
			
			manager.addMapping(mapping);
		}
	}
	
	private Datasource getDatasource(String sourceName) throws Exception{

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
	
}
