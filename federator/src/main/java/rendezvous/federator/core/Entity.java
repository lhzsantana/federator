package rendezvous.federator.core;

public class Entity {
	
	public Entity(String name, String id, String source){
		this.name=name;
		this.id=id;
		this.source=source;
	}

	public Entity(String name) {
		this.name=name;
	}

	private String name;
	private String id;
	private String source;
	
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
	
	
}
