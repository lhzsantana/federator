package rendezvous.federator.executor.cache;

import java.util.Set;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Value;

public interface Cache {
	
	public void evictAll();

	public Set<Value> get(Entity entity);
	public Value get(Field field);
	
	public void add(Entity entity);
	public void add(Value value);

	public boolean contains(Entity entity);


}
