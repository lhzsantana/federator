package rendezvous.federator.datasources.impl;

import rendezvous.federator.datasources.DataSource;

public abstract class DatasourceImpl extends DataSource {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
