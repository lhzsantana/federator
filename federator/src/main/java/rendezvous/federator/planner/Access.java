package rendezvous.federator.planner;

import rendezvous.federator.canonicalModel.DataElement;

public class Access {

	private DataElement dataElement;
	private Action action;
	private String database;
	private String query;
	
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public DataElement getDataElement() {
		return dataElement;
	}
	public void setDataElement(DataElement dataElement) {
		this.dataElement = dataElement;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}

}
