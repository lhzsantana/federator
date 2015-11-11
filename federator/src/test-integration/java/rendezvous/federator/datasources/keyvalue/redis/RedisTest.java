package rendezvous.federator.datasources.keyvalue.redis;

import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;

public class RedisTest {
	
	private final static Logger logger = Logger.getLogger(RedisTest.class);
	
	private Redis redis; 
	
	@Before
	public void setup() throws NumberFormatException, Exception{
		redis = new Redis();
	}
	
	@Test
	public void testConnect() throws Exception {
		redis.connect();
	}

	@Test
	public void testInsert() throws ParseException, Exception {

		Entity entity = new Entity("entity","2","");
		entity.setId(String.valueOf(Math.random()));
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value("entity","field1","value1",DataType.STRING.toString()));
		values.add(new Value("entity","field2","value2",DataType.STRING.toString()));
		values.add(new Value("entity","field3","value3",DataType.STRING.toString()));
		
		assertNotNull(redis.insert(entity, values));
	}

	@Test
	public void testGet() throws ParseException, Exception {
		
		Entity entity = new Entity("entity","2","");
		entity.setId(String.valueOf(Math.random()));
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value("entity","field1","value1",DataType.STRING.toString()));
		values.add(new Value("entity","field2","value2",DataType.STRING.toString()));
		values.add(new Value("entity","field3","value3",DataType.STRING.toString()));
		
		assertNotNull(redis.insert(entity, values));
				
		Hit hit = redis.get(entity);
	}

}
