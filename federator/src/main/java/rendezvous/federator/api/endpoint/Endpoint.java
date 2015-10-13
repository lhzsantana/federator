package rendezvous.federator.api.endpoint;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import rendezvous.federator.dictionary.DictionaryReader;
import rendezvous.federator.dictionary.impl.DictionaryReaderImpl;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.executor.impl.ExecutorImpl;
import rendezvous.federator.planner.Planner;
import rendezvous.federator.planner.impl.PlannerImpl;

public abstract class Endpoint {

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

	public Set<String> extractValues(String string) throws ParseException {

		JSONObject jsonObject = (JSONObject) parser.parse(string);

		Set<String> elements = new HashSet<String>();		
		
		for(Object object : jsonObject.values()){
			elements.add(object.toString());
		}

		return elements;
	}
}
