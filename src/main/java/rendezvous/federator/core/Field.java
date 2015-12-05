package rendezvous.federator.core;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Field {
	
	private final static Logger logger = Logger.getLogger(Field.class);

	public Field(String fieldName, Entity entity){
		this.fieldName = fieldName;
		this.entity = entity;
	}

	private String fieldName;
	private Entity entity;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
    public int hashCode() {
        return (getEntity().getName()+getFieldName()).hashCode();
    }

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Field)) {
			return false;
		}else{			
			Field field = (Field) obj;
			
			if(field.getEntity().getName().equals(this.getEntity().getName())
			 && field.getFieldName().equals(this.getFieldName())){
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
