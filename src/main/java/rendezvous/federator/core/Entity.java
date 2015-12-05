package rendezvous.federator.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Entity {

	private final static Logger logger = Logger.getLogger(Entity.class);

	private String name;
	private String id;
	private String source;
	private Integer mappingHash;
		
	public Integer getMappingHash() {
		return mappingHash;
	}

	public void setMappingHash(Integer mappingHash) {
		this.mappingHash = mappingHash;
	}

	private Set<Value> values; 
	private Set<Field> fields; 
		
	public Entity(String name, String id, String source){
		this.name=name;
		this.id=id;
		this.source=source;
	}

	public Entity(String name, Set<Value> values) {
		this.name=name;
		this.values=values;
	}

	public Entity(String name, List<Value> values) {
		this.name=name;
		this.values=new HashSet<Value>(values);
	} 

	public Entity(String name) {
		this.name=name;
	} 

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Set<Value> getValues() {
		return values;
	}

	public void setValues(Set<Value> values) {
		this.values = values;
	}
	
	public Set<Field> getFields() {
		return fields;
	}

	public void setFields(Set<Field> fields) {
		this.fields = fields;
	}


	public boolean isComplete() {
		
		if(this.getValues()!=null && this.getFields()!=null && this.getValues().size()==this.getFields().size()){
			return true;
		}
		
		return false;
	}

	@Override
    public int hashCode() {
        return (this.getName()+this.mappingHash).hashCode();
    }

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Entity)) {
			return false;
		}else{			
			Entity entity = (Entity) obj;
			
			if(this.hashCode()==entity.hashCode()){
				return true;
			}else{
				return false;
			}			
		}
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
