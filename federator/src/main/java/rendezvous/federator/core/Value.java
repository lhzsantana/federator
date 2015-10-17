package rendezvous.federator.core;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.datasources.document.mongodb.MongoDB;

public class Value {

	private final static Logger logger = Logger.getLogger(MongoDB.class);
	
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
	
	public Value(String entity, String field, String value, DataType type, DataSource dataSource) {
		this.setEntity(entity);
		this.field=field;
		this.value=value;
		this.type=type;
	
		Set<DataSource> sources = new HashSet<DataSource>();
		sources.add(dataSource);
		this.sources=sources;	
	}

	public Value(String entity, String field, String value, DataType type) {
		this.setEntity(entity);
		this.field=field;
		this.value=value;
		this.type=type;
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
	
	@Override
	public String toString() {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}
		
		return null;
	}
}
