package rendezvous.federator.datasources.document.mongodb;

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

public class MongoDBTest {

	private final static Logger logger = Logger.getLogger(MongoDBTest.class);
	
	private MongoDB mongodb; 
	
	@Before
	public void setup() throws NumberFormatException, Exception{
		mongodb = new MongoDB();
	}

	@After
	public void disconnect(){
		mongodb.close();
	}
	
	
	@Test
	public void testGetDataSourceType() {		
		assertEquals(mongodb.getDataSourceType(), DataSourceType.MONGODB.toString().toLowerCase());
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
		mongodb.connect();
	}

	@Test
	public void testInsertEmptyListShouldThrowException() throws NumberFormatException, Exception {
		
		mongodb.connect();
		
		Set<Value> values = new HashSet<Value>();
		
		mongodb.insert(new Entity("teste","1",""), values);		
	}

	@Test
	public void testInsertShouldWork() throws NumberFormatException, Exception {
		
		mongodb.connect();
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value("entity1","field1","value1",DataType.STRING.toString()));
		values.add(new Value("entity1","field2","value2",DataType.STRING.toString()));
		values.add(new Value("entity1","field3","value3",DataType.STRING.toString()));
		
		assertNotNull(mongodb.insert(new Entity("teste","1",""), values));		
	}

	@Test
	public void testGet() throws NumberFormatException, Exception {
		
		mongodb.connect();
		
		String entity = "entity1";
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value(entity,"field1","value1",DataType.STRING.toString()));
		values.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values.add(new Value(entity,"field3","value3",DataType.STRING.toString()));
		
		Entity e = new Entity(entity,"1","");
		
		String id = mongodb.insert(e, values);
		
		logger.info("The id of the inserted entity is <"+id+">");
		
		Hit hit = mongodb.get(e);
		
		for(Value value : hit.getValues()){
			logger.info("The value <"+value.getField()+"> and <"+value.getValue()+">");
		}
	}

	@Test
	public void testQuery() throws NumberFormatException, Exception {
		
		mongodb.connect();
		
		String entity = "entity1";
		
		Set<Value> values1 = new HashSet<Value>();
		values1.add(new Value(entity,"field1","xpto",DataType.STRING.toString()));
		values1.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values1.add(new Value(entity,"field3","value3",DataType.STRING.toString()));
		
		mongodb.insert(new Entity(entity,"1",""), values1);
		
		Set<Value> values2 = new HashSet<Value>();
		values2.add(new Value(entity,"field1","xpto",DataType.STRING.toString()));
		values2.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values2.add(new Value(entity,"field3","value3",DataType.STRING.toString()));
		
		mongodb.insert(new Entity(entity,"1",""), values2);
		
		Set<Value> values3 = new HashSet<Value>();
		values3.add(new Value(entity,"field1","xpto",DataType.STRING.toString()));
		values3.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values3.add(new Value(entity,"field3","value3",DataType.STRING.toString()));
		
		mongodb.insert(new Entity(entity,"1",""), values3);

		Set<Value> values4 = new HashSet<Value>();
		values4.add(new Value(entity,"field1","Não é pra vir",DataType.STRING.toString()));
		values4.add(new Value(entity,"field2","value2",DataType.STRING.toString()));
		values4.add(new Value(entity,"field3","value3",DataType.STRING.toString()));
		
		Entity e1 = new Entity(entity,"1","");
		
		mongodb.insert(e1, values4);
		
		Set<Value> valuesQuery = new HashSet<Value>();
		valuesQuery.add(new Value(entity,"field1","xpto",DataType.STRING.toString()));
			
		List<Hit> hits = mongodb.query(e1, valuesQuery);

		logger.info("--------------------------------------------------------------");
		
		for(Hit hit : hits){
			for(Value value : hit.getValues()){
				logger.info("The value <"+value.getField()+"> and <"+value.getValue()+">");
			}
		}
	}
}
