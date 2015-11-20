package rendezvous.federator.datasources.graph.neo4j;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Type;
import rendezvous.federator.core.Value;

public class Neo4JTest {

	private final static Logger logger = Logger.getLogger(Neo4JTest.class);
	
	private Neo4J neo4j; 
	
	@Before
	public void setup() throws NumberFormatException, Exception{
		neo4j = new Neo4J();
	}

	@After
	public void disconnect(){
		neo4j.close();
	}
	
	@Test
	public void testInsert() throws ParseException, Exception {

		Entity entity = new Entity("entity","1","");
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value("entity1","field1","value1",new Type(DataType.STRING.toString())));
		values.add(new Value("entity1","field2","value2",new Type(DataType.STRING.toString())));
		values.add(new Value("entity1","field3","value3",new Type(DataType.STRING.toString())));
		
		assertNotNull(neo4j.insert(entity, values));	
	}

	@Test
	public void testQuery() throws Exception {

		Entity entity = new Entity("entity","2","");
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value("entity",String.valueOf(Math.random()),"value1",new Type(DataType.STRING.toString())));
		
		assertNotNull(neo4j.insert(entity, values));
		
		assertNotNull(neo4j.query(entity, values));
		
		List<Hit> hits = neo4j.query(entity, values);
		
		assertEquals(1,hits.size());
	}

	@Test
	public void testGet() throws ParseException, Exception {
			
		Entity entity = new Entity("entity","2","");
		entity.setId(String.valueOf(Math.random()));
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value("entity","field1","value1",new Type(DataType.STRING.toString())));
		values.add(new Value("entity","field2","value2",new Type(DataType.STRING.toString())));
		values.add(new Value("entity","field3","value3",new Type(DataType.STRING.toString())));
		
		assertNotNull(neo4j.insert(entity, values));
		
		assertNotNull(neo4j.query(entity, values));
		
		Hit hit = neo4j.get(entity);
	
	}
}
