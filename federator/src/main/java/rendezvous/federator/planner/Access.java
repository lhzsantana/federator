package rendezvous.federator.planner;

import java.util.Set;

import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.dictionary.Value;

public class Access {

	private DataSource dataSource;
	private Action action;
	private String entity;
	private Set<Value> values;
	
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Set<Value> getValues() {
		return values;
	}
	public void setValues(Set<Value> values) {
		this.values = values;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
}
