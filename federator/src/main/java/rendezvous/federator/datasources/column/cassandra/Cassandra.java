package rendezvous.federator.datasources.column.cassandra;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
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
		
		try{
			session = cluster.connect("federator");
		}catch(Exception e){

			session = cluster.connect();
			String query = "CREATE KEYSPACE federator WITH replication "
					   + "= {'class':'SimpleStrategy', 'replication_factor':1}; ";
			
			session.execute(query);
			session = cluster.connect("federator");
		}		
	}
	@Override
	public void insertString(String table, String entity, String value) throws ParseException {
		
		JSONObject jsonObject = (JSONObject) parser.parse(value);
		
		String fieldList = "id,";
		String fieldListTable = "id uuid PRIMARY KEY,";
		String valueList = UUID.randomUUID().toString()+",";

		for(Object field:jsonObject.keySet()){
			fieldList+=field+",";
			fieldListTable+=field+" text,";
			valueList+= "'"+jsonObject.get(field)+"',";
		}
		
		try{
			session.execute("CREATE TABLE "+table+"."+entity+" (" +fieldListTable.substring(0,fieldListTable.length()-1)+ ");");
		}catch(Exception e){
			logger.error(e);
		}
		
		String cql ="INSERT INTO "+table+"."+entity+" ("+fieldList.substring(0,fieldList.length()-1)+") " +
			      "VALUES ("+valueList.substring(0,valueList.length()-1)+")";

		logger.debug(cql);
		
		session.execute(cql);
	}
}
