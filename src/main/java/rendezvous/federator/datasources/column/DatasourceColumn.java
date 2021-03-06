package rendezvous.federator.datasources.column;

import java.util.Set;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.datasources.QueryableDatasource;

public abstract class DatasourceColumn extends QueryableDatasource {

	protected JSONParser parser = new JSONParser();
	
	public abstract void createDataElements(Entity entity, Set<Field> fields);
}
