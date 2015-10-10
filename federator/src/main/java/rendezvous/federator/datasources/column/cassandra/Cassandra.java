package rendezvous.federator.datasources.column.cassandra;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import rendezvous.federator.datasources.column.DatasourceColumn;

public class Cassandra extends DatasourceColumn {

	private final static Logger logger = Logger.getLogger(Cassandra.class);
	private static Cluster cluster;
	private static Session session;
	
	@Override
	public String getDatabaseType() {
		return "Cassandra";
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
	public void connect() {
		
		logger.debug("Connecting to " + getDatabaseType());		
		
		cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		session = cluster.connect("demo");
	}

	public void insertString(String collection, String field, String value){
		
	}

	@Override
	public void insertString(String collection, String value) throws ParseException {
		// TODO Auto-generated method stub
		
	}
}
