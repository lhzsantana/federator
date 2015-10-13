package rendezvous.federator.canonicalModel;

import java.util.Set;

import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.dictionary.Value;

public class DataElement {

	private Set<Value> values;
	private String entity;
	private Set<Datasource> datasources;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
	public Set<Datasource> getDatasources() {
		return this.datasources;
	}

	public void setDataSources(Set<Datasource> datasources) {
		this.datasources = datasources;
	}

	public Set<Value> getValues() {
		return values;
	}

	public void setValues(Set<Value> values) {
		this.values = values;
	}

}
