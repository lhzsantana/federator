package rendezvous.federator.datasources.graph.neo4j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.core.Entity;
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
		values.add(new Value("entity1","field1","value1",DataType.STRING.toString()));
		values.add(new Value("entity1","field2","value2",DataType.STRING.toString()));
		values.add(new Value("entity1","field3","value3",DataType.STRING.toString()));
		
		assertNotNull(neo4j.insert(entity, values));	
	}

	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateDataElements() {
		fail("Not yet implemented");
	}

}
