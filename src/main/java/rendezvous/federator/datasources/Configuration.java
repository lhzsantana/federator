package rendezvous.federator.datasources;

import java.util.Map;

public class Configuration {

	private Map<String, Map<String, String>> datasources;

	public Map<String, Map<String, String>> getDatasources() {
		return datasources;
	}

	public void setDatasources(Map<String, Map<String, String>> datasources) {
		this.datasources = datasources;
	}	
}
