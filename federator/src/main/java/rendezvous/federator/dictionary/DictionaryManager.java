package rendezvous.federator.dictionary;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
	
	public static Map<Integer, Mapping> mappings = new HashMap<Integer, Mapping>();
	
	public static Mapping getMapping(Integer hash){
		return mappings.get(hash);
	}

	public static void addMapping(Mapping mapping){
		mappings.put(mapping.hashCode(), mapping);
	}
	
	public static boolean containsMapping(Integer hash){
		return  mappings.containsKey(hash);
	}
}
