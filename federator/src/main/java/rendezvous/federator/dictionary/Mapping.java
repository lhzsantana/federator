package rendezvous.federator.dictionary;

import java.util.List;
import java.util.Map;

public class Mapping {

	private Map<String, Map<String, Map<String, List<String>>>> entities;
	
	public Map<String, Map<String, Map<String, List<String>>>> getEntities() {
		return entities;
	}
	public void setEntities(Map<String, Map<String, Map<String, List<String>>>> entities) {
		this.entities = entities;
	}
}
