package rendezvous.federator.datasources.impl;

import rendezvous.federator.datasources.Datasource;

public abstract class DatasourceImpl extends Datasource {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
