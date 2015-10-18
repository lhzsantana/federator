package rendezvous.federator.datasources.document.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSourceType;
import rendezvous.federator.datasources.document.DatasourceDocument;

public class MongoDB extends DatasourceDocument {

	private final static Logger logger = Logger.getLogger(MongoDB.class);
	private static MongoClient mongoClient;
	private static DB db;
	private static DBCollection collection;
	private String name;

	public MongoDB() throws NumberFormatException, Exception{
		this.connect();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean connect() throws NumberFormatException, Exception {
		
		if (db == null) {			
			mongoClient = new MongoClient(getConfiguration().get("host"),
					Integer.valueOf(getConfiguration().get("port")));
			
			db = mongoClient.getDB(getConfiguration().get("database"));
			collection = db.getCollection(getConfiguration().get("database"));
			
			logger.info("Connected to " + getDataSourceType());
			
			return true;
		}
		
		return false;
	}
	
	public void close(){
		mongoClient.close();
	}
	
	@Override
	public void createDataElements(Entity entity, Set<Field> fields) {

		if(!db.collectionExists(entity.getName())){
			db.createCollection(entity.getName(), new BasicDBObject());
		}
	}

	@Override
	public String insert(Entity entity, Set<Value> values) throws Exception {

		if(values==null||values.isEmpty()||values.size()==0) throw new Exception("Cannot insert an empty list");
		
		String id = entity.getId();

		DBCollection collection = null;
		
		if(db.collectionExists(entity.getName())){
			collection = db.getCollection(entity.getName());
		}else{
			collection = db.createCollection(entity.getName(), new BasicDBObject());
		}
		
		DBObject document=new BasicDBObject();			
		document.put("rendezvous_id", id);
			
		for (Value value : values) {
			document.put(value.getField().getFieldName(), value.getValue());
			logger.info("Added field " + value.getField() + " and value " + value.getValue());
		}

		collection.insert(document);
		
		logger.info("Inserted the entity " + entity.getName());
		
		return id;
	}

	@Override
	public String getDataSourceType() {
		return DataSourceType.MONGODB.toString().toLowerCase();
	}

	@Override
	public Hit get(String entity, String id) throws Exception {
		
		if(!db.collectionExists(entity)){
			throw new Exception ("This entity does not exists");
		}else{
			collection = db.getCollection(entity);
			
			DBCursor cursor = collection.find(new BasicDBObject("rendezvous_id", id));
			
			while(cursor.hasNext()){
				
				logger.info("The document was found in MongoDB");
				
				Hit hit = new Hit();
				hit.setRelevance(1);
				
				List<Value> values = new ArrayList<Value>();

				DBObject document = cursor.next();
				
				for(String field : document.keySet()){
					values.add(new Value(entity,field,document.get(field).toString(),DataType.STRING.toString(), this));
				}
				
				hit.setValues(values);
				
				return hit;
			}			
		}
		
		return null;
	}

	@Override
	public List<Hit> query(String entity, Set<Value> queryValues) throws Exception {

		logger.info("Searching from MongoDB");

		if(!db.collectionExists(entity)){
			throw new Exception ("This entity does not exists");
		}else{
			collection = db.getCollection(entity);

			DBObject queryDocument=new BasicDBObject();			
			
			for(Value value : queryValues){
				queryDocument.put(value.getField().getFieldName(),value.getValue());
			}
			
			DBCursor cursor = collection.find(queryDocument);
			
			List<Hit> hits = new ArrayList<Hit>();
			
			while(cursor.hasNext()){
				
				logger.info("The document was found in MongoDB");
				
				Hit hit = new Hit();
				hit.setRelevance(1);
				
				List<Value> values = new ArrayList<Value>();

				DBObject document = cursor.next();
				
				for(String field : document.keySet()){
					values.add(new Value(entity,field,document.get(field).toString(),DataType.STRING.toString(), this));
				}
				
				hit.setValues(values);
				
				hits.add(hit);
			}
			
			return hits;			
		}		
	}
}
