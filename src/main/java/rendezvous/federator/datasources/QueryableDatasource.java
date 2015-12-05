package rendezvous.federator.datasources;

import java.util.List;
import java.util.Set;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;

public abstract class QueryableDatasource extends Datasource {
	
	public abstract List<Hit> query(Entity entity, Set<Value> values) throws Exception;
}
