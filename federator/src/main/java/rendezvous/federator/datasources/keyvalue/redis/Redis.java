package rendezvous.federator.datasources.keyvalue.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;

import redis.clients.jedis.Jedis;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSourceType;
import rendezvous.federator.datasources.keyvalue.KeyValueDatasource;

public class Redis extends KeyValueDatasource {

	private Jedis jedis;

	public Redis() throws Exception {
		this.connect();
	}

	@Override
	public String getDataSourceType() {
		return DataSourceType.REDIS.toString().toLowerCase();
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

		jedis = new Jedis(getConfiguration().get("redis.host"));
		jedis.ping();
	}

	@Override
	public String insert(Entity entity, Set<Value> values) throws ParseException, Exception {

		for (Value value : values) {
			jedis.set(entity.getId()+value.getField().getFieldName(), value.getValue());
		}

		return entity.getId();
	}

	@Override
	public List<Hit> query(String entity, Set<Value> values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hit get(Entity entity) throws Exception {

		Hit hit = new Hit();
		List<Value> values = new ArrayList<Value>();
		
		for (Field field : entity.getFields()) {
			String value = jedis.get(entity.getId()+field.getFieldName());
			
			values.add(new Value(entity.getName(),field.getFieldName(),value,null));
		}
		
		hit.setValues(values);
		
		return hit;
	}

	@Override
	public void close() {
		jedis.close();
		
	}

}
