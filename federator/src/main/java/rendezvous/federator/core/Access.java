package rendezvous.federator.core;

import java.util.Set;

import rendezvous.federator.datasources.DataSource;

public class Access {

	private DataSource dataSource;
	private Action action;
	private Entity entity;
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
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}	
}
