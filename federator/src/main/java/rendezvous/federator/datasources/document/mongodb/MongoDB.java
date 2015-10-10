package rendezvous.federator.datasources.document.mongodb;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import rendezvous.federator.datasources.document.DatasourceDocument;

public class MongoDB extends DatasourceDocument {

	private final static Logger logger = Logger.getLogger(MongoDB.class);
    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	private static DB db;

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	public void connect() {
		logger.debug("Connecting to " + getDatabaseType());

		if(db==null){
			db = mongoClient.getDB("federator");
		}
	}
	
	public void insertString(String collectionName, String field, String value){

		DBCollection collection = db.getCollection(collectionName);

		logger.debug(collectionName);
		logger.debug(field);
		logger.debug(value);
		
	    DBObject document=new BasicDBObject();
	    document.put(field, value);

		logger.debug(document.toString());
		
	    if(collection!=null){
	    	collection.insert(document);
	    }
	}

	@Override
	public String getDatabaseType() {
		return "MongoDB";
	}

}
