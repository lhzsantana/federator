package rendezvous.federator.dictionary;

import java.util.List;
import java.util.Map;

public class Dictionary {

	private String name;
	private Map<String, List<String>> oldEntities;
	private Map<String, List<String>> mappings;
	private Map<String, Map<String, List<String>>> types;
	private Map<String, Map<String, String>> datasources;
	private Map<String, Map<String, Map<String, String>>> finalEntities;
	private Map<String, Map<String, Map<String, List<String>>>> entities;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Map<String, String>> getDatasources() {
		return datasources;
	}
	public void setDatasources(Map<String, Map<String, String>> datasources) {
		this.datasources = datasources;
	}
	public Map<String, List<String>> getMappings() {
		return mappings;
	}
	public void setMappings(Map<String, List<String>> mappings) {
		this.mappings = mappings;
	}
	public Map<String, Map<String, List<String>>> getTypes() {
		return types;
	}
	public void setTypes(Map<String, Map<String, List<String>>> types) {
		this.types = types;
	}
	public Map<String, Map<String, Map<String, String>>> getFinalEntities() {
		return finalEntities;
	}
	public void setFinalEntities(Map<String, Map<String, Map<String, String>>> finalEntities) {
		this.finalEntities = finalEntities;
	}
	public Map<String, List<String>> getOldEntities() {
		return oldEntities;
	}
	public void setOldEntities(Map<String, List<String>> oldEntities) {
		this.oldEntities = oldEntities;
	}
	public Map<String, Map<String, Map<String, List<String>>>> getEntities() {
		return entities;
	}
	public void setEntities(Map<String, Map<String, Map<String, List<String>>>> entities) {
		this.entities = entities;
	}
}
