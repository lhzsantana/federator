package rendezvous.federator.core;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Field {
	
	private final static Logger logger = Logger.getLogger(Field.class);

	public Field(String fieldName, String entityName){
		this.fieldName = fieldName;
		this.entityName = entityName;
	}

	private String fieldName;
	private String entityName;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
    public int hashCode() {
        return (getEntityName()+getFieldName()).hashCode();
    }

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Field)) {
			return false;
		}else{			
			Field field = (Field) obj;
			
			if(field.getEntityName().equals(this.getEntityName())
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
