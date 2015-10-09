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
	private MongoClient mongoClient = new MongoClient();
	private DB db;

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	public void connect() {
		logger.debug("Connecting to " + getDatabaseType());

		db = mongoClient.getDB("testdb");
	}
	
	public void insertString(String collection, String field, String value){

		DBCollection table = db.getCollection(collection);

	    DBObject document=new BasicDBObject();
	    document.put(field, value);
	    
		table.insert(document);
	}

	@Override
	public String getDatabaseType() {
		return "MongoDB";
	}

}
