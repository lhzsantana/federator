package rendezvous.federator.canonicalModel.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.canonicalModel.DataSource;
import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.canonicalModel.Dictionary;
import rendezvous.federator.canonicalModel.DictionaryReader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class DictionaryReaderImpl implements DictionaryReader{
	
	final static Logger logger = Logger.getLogger(DictionaryReaderImpl.class);
	
	private static Map<String,DataElement> dictionary = new HashMap<String,DataElement>();

	public DataElement getDataElement(String string) throws JsonParseException, IOException{
		
		this.refreshDictionary();
		
		return dictionary.get(string);
	}
		
	private void refreshDictionary() throws JsonParseException, IOException{

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		
		Dictionary dictionaryMap = mapper.readValue(new File("rendezvouz.yml"), Dictionary.class);

		for(String entity: dictionaryMap.getEntities().keySet()){
			logger.debug("The entity "+entity+" was found in the dictionary");
						
			DataElement dataElement = new DataElement();
			dataElement.setName(entity);
			dataElement.setType(DataType.STRING);

			Set<DataSource> dataSources = new HashSet<DataSource>();	
			
			for(String datasourceName: dictionaryMap.getEntities().get(entity)){
				
				logger.debug("The datasource "+datasourceName+" was found in the dictionary for entity "+entity);

				DataSource dataSource = new DataSource();
				dataSource.setName(datasourceName);
				
				dataSources.add(dataSource);
			}
			
			dataElement.setDataSources(dataSources);

			dictionary.put(entity,dataElement);
		}
	}

}
