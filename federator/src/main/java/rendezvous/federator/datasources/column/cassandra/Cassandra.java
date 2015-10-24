package rendezvous.federator.datasources.column.cassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions.Definition;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AlreadyExistsException;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSourceType;
import rendezvous.federator.datasources.column.DatasourceColumn;

public class Cassandra extends DatasourceColumn {

	private final static Logger logger = Logger.getLogger(Cassandra.class);
	private static Cluster cluster = null;
	private static Session session;
	private String name;
	
	public Cassandra() throws Exception{
		this.connect();
	}

	@Override
	public String getDataSourceType() {
		return DataSourceType.CASSANDRA.toString().toLowerCase();
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
	public void connect() throws Exception {
		
		logger.info("Connecting to " + getDataSourceType());		
		
		if(cluster==null){
			
			cluster = Cluster.builder().addContactPoint(getConfiguration().get("host")).build();

			String keyspace = getConfiguration().get("keyspace");
			
			try{
				
				logger.info(keyspace);
				
				session = cluster.connect(keyspace);
				
			}catch(Exception e){
				
				logger.info("Creating keyspace", e);
							
				session = cluster.connect();
				
				String query = "CREATE KEYSPACE IF NOT EXISTS '"+keyspace+"' WITH replication "
						   + "= {'class':'SimpleStrategy', 'replication_factor':1}; ";
				
				session.execute(query);
				session = cluster.connect(keyspace);
			}			
		}else{
			logger.debug("Already connected");
		}		
	}
	
	@Override
	public void createDataElements(Entity entity, Set<Field> fields){

		String fieldListTable = "rendezvous_id text,";

		for(Field field:fields){
			fieldListTable+=field.getFieldName()+" text,";
		}
		
		String cql = "";
		
		try{
			cql = "CREATE TABLE "+entity.getName()+" (" +fieldListTable.substring(0,fieldListTable.length()-1)+ ", PRIMARY KEY (rendezvous_id));";
			
			session.execute(cql);
			
			//TODO: avoid this second loop
			for(Field field:fields){
				session.execute("CREATE INDEX ON "+entity.getName()+" ("+field.getFieldName()+");");
			}
			
		}catch(AlreadyExistsException e){
			logger.warn(e.getMessage() + " " + cql);
		}	
	}	
	
	@Override
	public String insert(Entity entity, Set<Value> values) throws ParseException {
				
		String id = entity.getId();
		String fieldList = "rendezvous_id,";
		String valueList = "'"+id+"',";

		for(Value value:values){
			fieldList+=value.getField().getFieldName()+",";
			valueList+= "'"+value.getValue()+"',";			
		}
		
		String cql = "INSERT INTO "+entity.getName()+" ("+fieldList.substring(0,fieldList.length()-1)+") " +
			      "VALUES ("+valueList.substring(0,valueList.length()-1)+")";

		logger.info(cql);
		
		session.execute(cql);
		
		return id;
	}

	@Override
	public Hit get(Entity entity) throws Exception {

		String cql = "SELECT * FROM "+entity+" WHERE rendezvous_id = '" + entity.getId() + "'";

		logger.info(cql);
		
		ResultSet result = session.execute(cql);
		
		List<Value> values = new ArrayList<Value>();
		
		for(Row row : result.all()){
			for(Definition definition : row.getColumnDefinitions().asList()){
				
				String field = definition.getName();
				
				if(!field.equals("rendezvous_id")){
					values.add(new Value(entity.getName(),field,row.getString(field),DataType.STRING.toString(), this));
					
					logger.info("Added a value for the field "+field);
				}
			}			
		}
		
		Hit hit = new Hit();
		hit.setRelevance(1);
		hit.setValues(values);
		
		return hit;
	}

	@Override
	public List<Hit> query(Entity entity, Set<Value> queryValues) throws Exception {
		
		logger.debug("Searching from Cassandra");
		
		if(queryValues==null || queryValues.isEmpty() || queryValues.size() == 0) throw new Exception("No parameter for the query");
		
		int index = 0;
		String cql = "SELECT * FROM "+entity+" WHERE ";

		for(Value value:queryValues){

			++index;
			
			cql+=value.getField().getFieldName()+"='"+value.getValue()+"'";
			if(index<queryValues.size()) cql+=" AND ";
			else cql+=" ALLOW FILTERING;";			
		}
				
		logger.info(cql);
		
		ResultSet result = session.execute(cql);
		
		List<Hit> hits = new ArrayList<Hit>();
		
		for(Row row : result.all()){

			Hit hit = new Hit();
			hit.setRelevance(1);
			
			List<Value> values = new ArrayList<Value>();
			
			for(Definition definition : row.getColumnDefinitions().asList()){
				
				String field = definition.getName();
				
				if(!field.equals("rendezvous_id")){
					values.add(new Value(entity.getName(),field,row.getString(field),DataType.STRING.toString(), this));
				}
			}
			
			hit.setValues(values);
			hits.add(hit);
		}		
		
		logger.info("<"+hits.size()+"> hits where found in Cassandra");
		
		return hits;
	}

	@Override
	public void close() {
		cluster.close();
	}
}
