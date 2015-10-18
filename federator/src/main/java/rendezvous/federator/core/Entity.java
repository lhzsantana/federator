package rendezvous.federator.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Entity {

	private String name;
	private String id;
	private String source;
	private Set<Value> values; 
		
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
	
	
}
