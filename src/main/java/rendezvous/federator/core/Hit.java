package rendezvous.federator.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Hit {

	private Entity entity;
	private List<Value> values;
	private Integer relevance;

	public List<Value> getValues() {
		return values;
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

	public Integer getRelevance() {
		return relevance;
	}

	public void setRelevance(Integer relevance) {
		this.relevance = relevance;
	}

	@Override
	public String toString() {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
		
		}
		
		return null;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String pretty() throws InconsistencyException {
		
		Map<String, String> mergedValues=new HashMap<String,String>();
		List<String> finalList = new ArrayList<String>();
		
		Source source = new Source();
		
		source.setEntity(entity.getName());
		source.setId(entity.getId());
		
		for(Value value: values){
			
			String expectedValue = mergedValues.get(value.getField().getFieldName());
			
			if(expectedValue==null||expectedValue.equals(value.getValue())){
				mergedValues.put(value.getField().getFieldName(), value.getValue());
				
				finalList.add(value.getField().getEntity().getName()+":"+value.getValue());
			}else{
				throw new InconsistencyException();
			}
		}	
		
		source.setValues(finalList);
		
		return source.toString();
	}
	
	private class Source{

		private String entity;
		private String id;
		private List<String> values;
		
		public String getEntity() {
			return entity;
		}
		public void setEntity(String entity) {
			this.entity = entity;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public List<String> getValues() {
			return values;
		}
		public void setValues(List<String> values) {
			this.values = values;
		}

		@Override
		public String toString() {
			
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				return mapper.writeValueAsString(this);
			} catch (JsonProcessingException e) {
			
			}
			
			return null;
		}
	}
}
