package rendezvous.federator.core;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.datasources.document.mongodb.MongoDB;

public class Value {

	private final static Logger logger = Logger.getLogger(Value.class);
	
	private Field field;
	private Entity entity;
	private String value;
	private String type;
	private Set<Datasource> sources;
	
	public Value(String entity, String field, String value, String type, Set<Datasource> sources){
		this.entity=new Entity(entity);
		this.field=new Field(field,entity);
		this.value=value;
		this.type=type;
		this.sources=sources;
	}
		
	public Value(String entity, String field, String value, String type, Datasource dataSource) {
		this.entity=new Entity(entity);
		this.field=new Field(field,entity);
		this.value=value;
		this.type=type;
	
		Set<Datasource> sources = new HashSet<Datasource>();
		sources.add(dataSource);
		this.sources=sources;	
	}

	public Value(String entity, String field, String value, String type) {
		this.entity=new Entity(entity);
		this.field=new Field(field,entity);
		this.value=value;
		this.type=type;
	}

	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Set<Datasource> getSources() {
		return sources;
	}

	public void setSources(Set<Datasource> sources) {
		this.sources = sources;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
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
