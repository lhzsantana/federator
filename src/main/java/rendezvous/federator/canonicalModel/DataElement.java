package rendezvous.federator.canonicalModel;

import java.util.Map;
import java.util.Set;

import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.Datasource;

public class DataElement {

	private Set<Value> values;
	private String entity;
	private Map<String, Datasource> datasources;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public Map<String, Datasource> getDatasources() {
		return this.datasources;
	}

	public void setDataSources(Map<String, Datasource> datasources) {
		this.datasources = datasources;
	}

	public Set<Value> getValues() {
		return values;
	}

	public void setValues(Set<Value> values) {
		this.values = values;
	}

}
