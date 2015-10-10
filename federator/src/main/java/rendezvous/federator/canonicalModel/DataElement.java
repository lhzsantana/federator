package rendezvous.federator.canonicalModel;

import java.util.Set;

import rendezvous.federator.datasources.Datasource;

public class DataElement {

	private String value;
	private String name;
	private DataType type;
	private Set<Datasource> datasources;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public Set<Datasource> getDatasources() {
		return this.datasources;
	}

	public void setDataSources(Set<Datasource> datasources) {
		this.datasources = datasources;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
