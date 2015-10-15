package rendezvous.federator.datasources.document.mongodb;

import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import rendezvous.federator.api.Hit;
import rendezvous.federator.datasources.document.DatasourceDocument;
import rendezvous.federator.dictionary.Value;

public class MongoDB extends DatasourceDocument {
	
	private final static Logger logger = Logger.getLogger(MongoDB.class);
    private static MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	private static DB db;
	private static DBCollection collection;
	private String name;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}
	
	public void connect() {
		if(db==null){
			db = mongoClient.getDB("federator");			
			//collection = db.getCollection("collection");
			logger.debug("Connected to " + getDatabaseType());			
		}
	}
	
	public String insert(String collectionName, String entity, Set<Value> values) throws ParseException {

		String id = UUID.randomUUID().toString();
		
		try{
			//this.connect();

			//db.createCollection(entity, null);
		    //DBObject document=new BasicDBObject();
			//document.put("rendezvous_id", id);
			for(Value value:values){
			    //document.put(value.getField(), value.getValue());
			    logger.info("Added field "+value.getField()+" and value "+value.getValue());
			}
	
		    logger.info("Inserted the entity "+entity);
		}catch(Exception e){
			logger.error(e);
		}
	    /*
	    if(collection!=null){
	    	collection.insert(document);
	    }*/
		
		return id; 
	}

	@Override
	public String getDatabaseType() {
		return "MongoDB";
	}

	@Override
	public Hit get(String string, String entity, Set<Value> values) {
		logger.debug("Getting from MongoDB");
		return null;
	}
}
