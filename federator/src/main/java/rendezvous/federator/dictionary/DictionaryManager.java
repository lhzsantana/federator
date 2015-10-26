package rendezvous.federator.dictionary;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
	
	public Map<Integer, Rendezvous> mappings = new HashMap<Integer, Rendezvous>();
	
	public Rendezvous getMapping(Integer hash){
		return mappings.get(hash);
	}

	public void addMapping(Rendezvous mapping){
		mappings.put(mapping.hashCode(), mapping);
	}
	
	public boolean containsMapping(Integer hash){
		return mappings.containsKey(hash);
	}
}
