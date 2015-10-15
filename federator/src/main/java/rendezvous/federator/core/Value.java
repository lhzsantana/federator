package rendezvous.federator.core;

import java.util.Set;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.datasources.DataSource;

public class Value {
	
	private String entity;
	private String field;
	private String value;
	private DataType type;
	private Set<DataSource> sources;
	
	public Value(String entity, String field, String value, DataType type, Set<DataSource> sources){
		this.setEntity(entity);
		this.field=field;
		this.value=value;
		this.type=type;
		this.sources=sources;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public DataType getType() {
		return type;
	}
	public void setType(DataType type) {
		this.type = type;
	}

	public Set<DataSource> getSources() {
		return sources;
	}

	public void setSources(Set<DataSource> sources) {
		this.sources = sources;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
}
