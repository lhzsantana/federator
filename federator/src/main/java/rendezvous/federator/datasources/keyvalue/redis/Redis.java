package rendezvous.federator.datasources.keyvalue.redis;

import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.keyvalue.KeyValueDatasource;

public class Redis extends KeyValueDatasource {

	@Override
	public String getDataSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean connect() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String insert(Entity entity, Set<Value> values) throws ParseException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hit> query(String entity, Set<Value> values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hit get(String entity, String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createDataElements(Entity entity, Set<Field> fields) {
		// TODO Auto-generated method stub

	}

}
