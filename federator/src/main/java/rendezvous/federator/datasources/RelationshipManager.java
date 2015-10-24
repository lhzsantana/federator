package rendezvous.federator.datasources;

import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;

public class RelationshipManager extends QueryableDatasource {

	@Override
	public String getDataSourceType() {
		// TODO Auto-generated method stub
		return "Relationship";
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
	public void connect() throws Exception {
	}

	@Override
	public String insert(Entity entity, Set<Value> values) throws ParseException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hit get(Entity entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Hit> query(Entity entity, Set<Value> values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
