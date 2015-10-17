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
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSourceType;

public class MongoDBTest {

	private final static Logger logger = Logger.getLogger(MongoDBTest.class);
	
	private MongoDB mongodb; 
	
	@Before
	public void setup(){
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
		assertEquals(mongodb.connect(),true);
		assertEquals(mongodb.connect(),false);
	}

	@Test
	public void testInsertEmptyListShouldThrowException() throws NumberFormatException, Exception {
		
		mongodb.connect();
		
		Set<Value> values = new HashSet<Value>();
		
		mongodb.insert("teste", values);		
	}

	@Test
	public void testInsertShouldWork() throws NumberFormatException, Exception {
		
		mongodb.connect();
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value("entity1","field1","value1",DataType.STRING));
		values.add(new Value("entity1","field2","value2",DataType.STRING));
		values.add(new Value("entity1","field3","value3",DataType.STRING));
		
		assertNotNull(mongodb.insert("teste", values));		
	}

	@Test
	public void testGet() throws NumberFormatException, Exception {
		
		mongodb.connect();
		
		String entity = "entity1";
		
		Set<Value> values = new HashSet<Value>();
		values.add(new Value(entity,"field1","value1",DataType.STRING));
		values.add(new Value(entity,"field2","value2",DataType.STRING));
		values.add(new Value(entity,"field3","value3",DataType.STRING));
		
		String id = mongodb.insert(entity, values);
		
		logger.info("The id of the inserted entity is <"+id+">");
		
		Hit hit = mongodb.get(entity, id);
		
		for(Value value : hit.getValues()){
			logger.info("The value <"+value.getField()+"> and <"+value.getValue()+">");
		}
	}

	@Test
	public void testQuery() throws NumberFormatException, Exception {
		
		mongodb.connect();
		
		String entity = "entity1";
		
		Set<Value> values1 = new HashSet<Value>();
		values1.add(new Value(entity,"field1","xpto",DataType.STRING));
		values1.add(new Value(entity,"field2","value2",DataType.STRING));
		values1.add(new Value(entity,"field3","value3",DataType.STRING));
		
		mongodb.insert(entity, values1);
		
		Set<Value> values2 = new HashSet<Value>();
		values2.add(new Value(entity,"field1","xpto",DataType.STRING));
		values2.add(new Value(entity,"field2","value2",DataType.STRING));
		values2.add(new Value(entity,"field3","value3",DataType.STRING));
		
		mongodb.insert(entity, values2);
		
		Set<Value> values3 = new HashSet<Value>();
		values3.add(new Value(entity,"field1","xpto",DataType.STRING));
		values3.add(new Value(entity,"field2","value2",DataType.STRING));
		values3.add(new Value(entity,"field3","value3",DataType.STRING));
		
		mongodb.insert(entity, values3);

		Set<Value> values4 = new HashSet<Value>();
		values4.add(new Value(entity,"field1","Não é pra vir",DataType.STRING));
		values4.add(new Value(entity,"field2","value2",DataType.STRING));
		values4.add(new Value(entity,"field3","value3",DataType.STRING));
		
		mongodb.insert(entity, values4);
		
		Set<Value> valuesQuery = new HashSet<Value>();
		valuesQuery.add(new Value(entity,"field1","xpto",DataType.STRING));
			
		List<Hit> hits = mongodb.query(entity, valuesQuery);

		logger.info("--------------------------------------------------------------");
		
		for(Hit hit : hits){
			for(Value value : hit.getValues()){
				logger.info("The value <"+value.getField()+"> and <"+value.getValue()+">");
			}
		}
	}
}
