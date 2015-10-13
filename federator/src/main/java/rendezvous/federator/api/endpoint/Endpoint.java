package rendezvous.federator.api.endpoint;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import rendezvous.federator.api.endpoint.impl.InsertEndpoint;
import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.dictionary.DictionaryReader;
import rendezvous.federator.dictionary.Value;
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

	public Set<Value> extractValues(String string) throws ParseException {
		
		JSONObject jsonObject = (JSONObject) parser.parse(string);

		Set<Value> values = new HashSet<Value>();

		for(Object object : jsonObject.keySet()){
			
			String rawField = object.toString();
			String rawValue = jsonObject.get(object).toString();

			logger.debug("The value "+rawValue+" was extracted for field "+rawField);
			
			Value value = new Value(rawField,rawValue,DataType.STRING);
			values.add(value);			
		}
		 
		return values;
	}

	public JSONObject extractEntities(String string) throws ParseException {

		JSONObject jsonObject = (JSONObject) parser.parse(string);
		
		return jsonObject;
	}
}
