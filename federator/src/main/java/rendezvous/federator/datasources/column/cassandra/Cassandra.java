package rendezvous.federator.datasources.column.cassandra;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.column.DatasourceColumn;

public class Cassandra extends DatasourceColumn {

	private final static Logger logger = Logger.getLogger(Cassandra.class);
	private static Cluster cluster;
	private static Session session;
	private String name;
	
	@Override
	public String getDatabaseType() {
		return "Cassandra";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public void connect() {
		
		logger.debug("Connecting to " + getDatabaseType());		
		
		if(cluster==null){
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
		}else{
			logger.debug("Already connected");
		}
	}
	@Override
	public String insert(String table, String entity, Set<Value> values) throws ParseException {
		
		String id = UUID.randomUUID().toString();
		String fieldList = "rendezvous_id,";
		String fieldListTable = "rendezvous_id uuid PRIMARY KEY,";
		String valueList = id+",";

		for(Value value:values){
			fieldList+=value.getField()+",";
			fieldListTable+=value.getField()+" text,";
			valueList+= "'"+value.getValue()+"',";
		}
		
		try{
			session.execute("CREATE TABLE "+table+"."+entity+" (" +fieldListTable.substring(0,fieldListTable.length()-1)+ ");");
		}catch(Exception e){
			logger.error(e);
		}
		
		String cql ="INSERT INTO "+table+"."+entity+" ("+fieldList.substring(0,fieldList.length()-1)+") " +
			      "VALUES ("+valueList.substring(0,valueList.length()-1)+")";

		logger.info(cql);
		
		//session.execute(cql);
		
		return id;
	}

	@Override
	public Hit get(String string, String entity, Set<Value> values) {
		logger.debug("Getting from Cassandra");
		return null;
	}

	@Override
	public List<Hit> query(String string, String entity, Set<Value> values) {
		
		logger.debug("Searching from Cassandra");
		
		for(Value value:values){
			logger.debug("The following values <"+value.getValue()+">");
		}
		
		return null;
	}
}
