package rendezvous.federator.cache;

import java.util.List;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;

public interface Cache {

	public List<Hit> get(Entity entity);
	public List<Hit> get(Field field);
	
	public void add(Entity entity);
	public void add(Value value);

}
