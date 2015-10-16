package rendezvous.federator.datasources;

import static org.junit.Assert.*;

import org.junit.Test;

import rendezvous.federator.datasources.column.cassandra.Cassandra;
import rendezvous.federator.datasources.document.mongodb.MongoDB;

public class DataSourceTest {

	@Test
	public void testConfiguration() throws Exception {
		
		DataSource source = new MongoDB();		
		assertNotNull(source.getConfiguration());

		source = new Cassandra();
		assertNotNull(source.getConfiguration());
		
	}

}
