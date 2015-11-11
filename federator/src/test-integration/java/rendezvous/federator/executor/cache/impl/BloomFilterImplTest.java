package rendezvous.federator.executor.cache.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Value;
import rendezvous.federator.executor.cache.Cache;

public class BloomFilterImplTest {
	
	private final static Logger logger = Logger.getLogger(BloomFilterImplTest.class);

	Cache cache = new BloomFilterImpl();
	
	@Test
	public void testAddEntity() {
		
		String n = "name1";
		
		Entity e = new Entity(n);

		Set<Value> values = new HashSet<Value>();
		for(int i=0;i<1;i++){
			Value value = new Value(n, "a"+i, "a"+i, "a"+i);
			values.add(value);
		}
		e.setValues(values);
		
		cache.add(e);

		Set<Value> cached = cache.get(e);
		
		assertArrayEquals(e.getValues().toArray(), cached.toArray());		
	}

	@Test
	public void testAddValue() {	
		logger.info("Starting test testAddValue");
				
		for(int i=0;i<10000;i++){
			Value value = new Value("a"+i, "a"+i, "a"+i, "a"+i);
			cache.add(value);
		}

		Value value1 = new Value("a", "a", "a", "a");
		cache.add(value1);
		
		Field field = new Field("a","a");
		
		Value v = cache.get(field);
		
		assertEquals(value1,v);
	}

	@Test
	public void testEvictionValue() {	

		Value value1 = new Value("a", "a", "a", "a");
		cache.add(value1);
		
		for(int i=0;i<10000;i++){
			Value value = new Value("a"+i, "a"+i, "a"+i, "a"+i);
			cache.add(value);
		}
		
		Field field = new Field("a","a");
		
		Value v = cache.get(field);
		
		assertNull(v);
	}
}
