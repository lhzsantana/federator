package rendezvous.federator.api.endpoint;

import java.io.IOException;
import java.util.Set;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.canonicalModel.DictionaryReader;
import rendezvous.federator.canonicalModel.impl.DictionaryReaderImpl;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.executor.ExecutorImpl;
import rendezvous.federator.planner.Planner;
import rendezvous.federator.planner.PlannerImpl;

public abstract class Endpoint {

	private static final Gson gson = new Gson();
	protected final DictionaryReader dictionary = new DictionaryReaderImpl();
	protected final Planner planner = new PlannerImpl();
	protected final Executor executor = new ExecutorImpl();
	protected JsonFactory factory = new JsonFactory();
	protected ObjectMapper mapper = new ObjectMapper(factory);
	private JSONParser parser = new JSONParser();

	public static boolean isJSONValid(String JSON_STRING) {
		try {
			gson.fromJson(JSON_STRING, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}

	public Set<DataElement> extractDataElements(String string) throws JsonParseException, JsonMappingException, IOException, ParseException {

		JSONObject jsonObject = (JSONObject) parser.parse(string);

		for(Object object : jsonObject.keySet()){
			System.out.println(object);
		}

		return null;
	}
}
