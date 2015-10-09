package rendezvous.federator.dictionary.impl;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.datasources.column.cassandra.Cassandra;
import rendezvous.federator.datasources.document.mongodb.MongoDB;
import rendezvous.federator.dictionary.Dictionary;
import rendezvous.federator.dictionary.DictionaryReader;

public class DictionaryReaderImpl implements DictionaryReader {

	final static Logger logger = Logger.getLogger(DictionaryReaderImpl.class);

	private static Map<String, DataElement> dictionary = new HashMap<String, DataElement>();

	public DataElement getDataElement(String string) throws Exception {

		this.refreshDictionary();

		return dictionary.get(string);
	}

	public Dictionary refreshDictionary() throws Exception {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

		Dictionary dictionaryMap = mapper.readValue(new File("rendezvouz.yml"), Dictionary.class);

		for (String entity : dictionaryMap.getEntities().keySet()) {
			
			logger.debug("The entity " + entity + " was found in the dictionary");

			DataElement dataElement = new DataElement();
			dataElement.setName(entity);
			dataElement.setType(DataType.STRING);

			Set<Datasource> datasources = new HashSet<Datasource>();

			for (Map<String, Map<String, List<String>>> entities : dictionaryMap.getEntities().values()) {

				for (Map<String, List<String>> fields : entities.values()) {
						
					for (String typesOrDatasources : fields.keySet()) {
						
						if(typesOrDatasources.equals("type")){

							//logger.debug(typesOrDatasources);
							//logger.debug(fields.get(typesOrDatasources));
							
						}
						
						if(typesOrDatasources.equals("source")){

							for(String datasourceName : fields.get(typesOrDatasources)){
	
								logger.debug("The datasource " + datasourceName + " was found in the dictionary for entity " + entity);
	
								Datasource datasource = null;
	
								switch (datasourceName) {
								
									case "mongodb":
										datasource = new MongoDB();
										break;
									case "cassandra":
										datasource = new Cassandra();
										break;	
									default:
										throw new Exception("No datasource found!");
								}
	
								datasources.add(datasource);
							}
						}
					}
				}
			}
			dataElement.setDataSources(datasources);
			dictionary.put(entity, dataElement);
		}

		return dictionaryMap;
	}

	public Set<Datasource> getDatasources() throws Exception {

		this.refreshDictionary();

		Set<Datasource> datasources = new HashSet<Datasource>();

		for (DataElement dataElement : dictionary.values()) {
			for (Datasource dataSource : dataElement.getDatasources()) {
				datasources.add(dataSource);
			}
		}

		return datasources;
	}

}
