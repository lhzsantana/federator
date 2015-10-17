package rendezvous.federator.api.endpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.gson.Gson;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.dictionary.DictionaryReader;
import rendezvous.federator.dictionary.impl.DictionaryReaderImpl;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.executor.impl.ExecutorImpl;
import rendezvous.federator.planner.Planner;
import rendezvous.federator.planner.impl.PlannerImpl;

public abstract class Endpoint {

	final static Logger logger = Logger.getLogger(Endpoint.class);

	private static final Gson gson = new Gson();
	protected final DictionaryReader dictionary = new DictionaryReaderImpl();
	protected final Planner planner = new PlannerImpl();
	protected final Executor executor = new ExecutorImpl();	
	private JSONParser parser = new JSONParser();

	public static boolean isJSONValid(String JSON_STRING) {
		try {
			gson.fromJson(JSON_STRING, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}

	public JSONObject extractElements(String string) throws ParseException {

		JSONObject jsonObject = (JSONObject) parser.parse(string);
		
		return jsonObject;
	}

	public Set<Value> extractValues(String string) throws JsonParseException, IOException, Exception {

		Set<Value> values = new HashSet<Value>();
		
		JSONObject jsonEntity = (JSONObject) parser.parse(string);

		for(Object entity : jsonEntity.keySet()){

			String rawEntity = entity.toString();
			
			JSONObject jsonField = (JSONObject) parser.parse(jsonEntity.get(entity).toString());
			
			for(Object field : jsonField.keySet()){
			
				String rawField = field.toString();
				String rawValue = jsonField.get(field).toString();
	
				logger.debug("The value <"+rawValue+"> was extracted for field <"+rawField+"> of the entity <"+rawEntity+">");
				
				Set<DataSource> sources = dictionary.getDatasources(new Field(rawField,rawEntity));
				
				values.add(new Value(rawEntity,rawField,rawValue,DataType.STRING, sources));			
			}
		}
		 
		return values;
	}

	public List<String> extractEntities(String json) throws ParseException {

		JSONObject jsonEntity = (JSONObject) parser.parse(json);

		List<String> entities = new ArrayList<String>();
		
		for(Object entity : jsonEntity.keySet()){
			entities.add(entity.toString());
		}
		
		return entities;
	}

	public String extractEntity(String json) throws ParseException {
		return extractEntities(json).get(0);
	}	
}
