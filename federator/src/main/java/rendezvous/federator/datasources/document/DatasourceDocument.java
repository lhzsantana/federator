package rendezvous.federator.datasources.document;

import java.util.Set;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.datasources.DataSource;

public abstract class DatasourceDocument extends DataSource {

	protected JSONParser parser = new JSONParser();

	public abstract void createDataElements(Entity entity, Set<Field> fields);
}
