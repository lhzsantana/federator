package rendezvous.federator.datasources.column.cassandra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSourceType;

public class CassandraTest {

	private final static Logger logger = Logger.getLogger(CassandraTest.class);
	
	private Cassandra cassandra; 
	
	@Before
	public void setup() throws Exception{
		cassandra = new Cassandra();
	}

	@After
	public void disconnect(){
		cassandra.close();
	}
	
	
	@Test
	public void testGetDataSourceType() {		
		assertEquals(cassandra.getDataSourceType(), DataSourceType.MONGODB.toString().toLowerCase());
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testConnect() throws NumberFormatException, Exception {
		cassandra.connect();
	}

	@Test
	public void testInsertEmptyListShouldThrowException() throws NumberFormatException, Exception {
		
		cassandra.connect();
		
		Set<Value> values = new HashSet<Value>();
		
		Entity entity = new Entity("teste","1","");
		
		cassandra.insert(entity, values);		
	}

	@Test
	public void testInsertShouldWork() throws NumberFormatException, Exception {
		
		cassandra.connect();
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value("entity11","field1","value1",DataType.STRING.toString()));
		values.add(new Value("entity11","field2","value2",DataType.STRING.toString()));
		values.add(new Value("entity11","field3","value3",DataType.STRING.toString()));
		
		Entity entity = new Entity("entity11","1","");
		
		assertNotNull(cassandra.insert(entity, values));		
	}

	@Test
	public void testGet() throws NumberFormatException, Exception {
		
		cassandra.connect();
		
		String entity = "entity12";
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value(entity,"field1","value1",DataType.STRING.toString()));
		values.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values.add(new Value(entity,"field3","value3",DataType.STRING.toString()));

		Entity entityObjec = new Entity(entity,"1","");
		
		String id = cassandra.insert(entityObjec, values);
		
		logger.info("The id of the inserted entity is <"+id+">");
		
		Hit hit = cassandra.get(entityObjec);
		
		for(Value value : hit.getValues()){
			logger.info("The value <"+value.getField()+"> and <"+value.getValue()+">");
		}
	}

	@Test
	public void testQuery() throws NumberFormatException, Exception {
		
		cassandra.connect();
		
		String entity = "entity13245";
		
		Set<Value> values1 = new HashSet<Value>();
		values1.add(new Value(entity,"field1","xpto",DataType.STRING.toString()));
		values1.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values1.add(new Value(entity,"field3","value3",DataType.STRING.toString()));
		
		cassandra.insert(new Entity(entity,"1",""), values1);
		
		Set<Value> values2 = new HashSet<Value>();
		values2.add(new Value(entity,"field1","xpto",DataType.STRING.toString()));
		values2.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values2.add(new Value(entity,"field3","value3",DataType.STRING.toString()));
		
		cassandra.insert(new Entity(entity,"2",""), values2);
		
		Set<Value> values3 = new HashSet<Value>();
		values3.add(new Value(entity,"field1","xpto",DataType.STRING.toString()));
		values3.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values3.add(new Value(entity,"field3","value3",DataType.STRING.toString()));
		
		cassandra.insert(new Entity(entity,"3",""), values3);

		Set<Value> values4 = new HashSet<Value>();
		values4.add(new Value(entity,"field1","Não é pra vir",DataType.STRING.toString()));
		values4.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values4.add(new Value(entity,"field3","value3",DataType.STRING.toString()));
		
		cassandra.insert(new Entity(entity,"3",""), values4);
		
		Set<Value> valuesQuery = new HashSet<Value>();
		valuesQuery.add(new Value(entity,"field1","xpto",DataType.STRING.toString()));
		valuesQuery.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
			
		List<Hit> hits = cassandra.query(entity, valuesQuery);

		logger.info("--------------------------------------------------------------");
		
		for(Hit hit : hits){
			for(Value value : hit.getValues()){
				logger.info("The value <"+value.getField()+"> and <"+value.getValue()+">");
			}
			logger.info("--------------------------------------------------------------");
		}
	}
}
