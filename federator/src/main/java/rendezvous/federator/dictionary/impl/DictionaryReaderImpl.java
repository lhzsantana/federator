package rendezvous.federator.dictionary.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Type;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.datasources.RelationshipManager;
import rendezvous.federator.datasources.column.cassandra.Cassandra;
import rendezvous.federator.datasources.document.mongodb.MongoDB;
import rendezvous.federator.datasources.graph.neo4j.Neo4J;
import rendezvous.federator.datasources.keyvalue.redis.Redis;
import rendezvous.federator.dictionary.DictionaryManager;
import rendezvous.federator.dictionary.DictionaryReader;
import rendezvous.federator.dictionary.Mapping;

public class DictionaryReaderImpl implements DictionaryReader {
	
	private final static Logger logger = Logger.getLogger(DictionaryReaderImpl.class);
	
	private final static DictionaryManager manager = new DictionaryManager();

	private final static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

	@Override
	public Map<Entity, Map<Datasource, Set<Field>>> refreshDictionary(String newMapping) throws Exception {

		Mapping mapping = mapper.readValue(newMapping, Mapping.class);

		Integer mappingHash = mapping.hashCode();

		if (!manager.containsMapping(mappingHash)) {

			for (String entityName : mapping.getEntities().keySet()) {

				logger.info("The entity <" + entityName + "> was found in the dictionary");

				Entity entity = new Entity(entityName);

				entity.setMappingHash(mappingHash);

				Set<Field> fields = new HashSet<Field>();

				Map<Field, Set<Datasource>> dictionaryFieldDatasources = new HashMap<Field, Set<Datasource>>();
				Map<Datasource, Set<Field>> dictionaryDatasourceFields = new HashMap<Datasource, Set<Field>>();
				Map<Field, Type> dictionaryFieldType = new HashMap<Field, Type>();

				for (String fieldName : mapping.getEntities().get(entity.getName()).keySet()) {

					logger.info("The field <" + fieldName + "> of entity <" + entityName + "> was found in the dictionary");

					Field field = new Field(fieldName, entity);
					fields.add(field);
					
					Type type = new Type(mapping.getEntities()
						.get(entity.getName())
						.get(field.getFieldName())
						.get("type").get(0)
					);
									
					dictionaryFieldType.put(field, type);

					Set<Datasource> datasources = new HashSet<Datasource>();
					
					for (String datasourceName : mapping.getEntities().get(entity.getName()).get(field.getFieldName())
							.get("source")) {

						logger.info("The datasource <" + datasourceName + "> for field <" + fieldName + "> of entity <"
								+ entityName + "> was found in the dictionary");

						Datasource datasource = this.getDatasource(datasourceName);

						Set<Field> previousFields = dictionaryDatasourceFields.get(datasource);

						if (previousFields == null) {
							previousFields = new HashSet<Field>();
						}

						previousFields.add(field);

						dictionaryDatasourceFields.put(datasource, previousFields);
						datasources.add(datasource);
					}					
					
					dictionaryFieldDatasources.put(field, datasources);
					dictionaryEntityFieldType.put(entity, dictionaryFieldType);
				}

				entity.setFields(fields);

				dictionaryEntityDatasourceFields.put(entity, dictionaryDatasourceFields);
			}

			manager.addMapping(mapping);
		}

		return dictionaryEntityDatasourceFields;
	}

	private Datasource getDatasource(String sourceName) throws Exception {

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
			throw new Exception("The datasource " + sourceName + " is invalid");
		}

		source.setName(sourceName);

		return source;
	}

}
