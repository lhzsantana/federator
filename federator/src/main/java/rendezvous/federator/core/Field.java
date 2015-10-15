package rendezvous.federator.core;

public class Field {

	public Field(String fieldName, String entityName) {
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
}
