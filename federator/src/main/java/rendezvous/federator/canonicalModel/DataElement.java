package rendezvous.federator.canonicalModel;

import java.util.Set;

public class DataElement {

	private String name;
	private DataType type;
	private Set<DataSource> dataSources;

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

	public Set<DataSource> getDataSources() {
		return dataSources;
	}

	public void setDataSources(Set<DataSource> dataSources) {
		this.dataSources = dataSources;
	}

}
