package rendezvous.federator.dictionary;

import rendezvous.federator.canonicalModel.DataType;

public class Value {
	
	private String field;
	private String value;
	private DataType type;
	
	public Value(String field, String value, DataType type){
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
}
