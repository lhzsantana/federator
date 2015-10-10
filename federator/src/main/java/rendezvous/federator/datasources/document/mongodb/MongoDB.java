package rendezvous.federator.datasources.document.mongodb;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import rendezvous.federator.datasources.document.DatasourceDocument;

public class MongoDB extends DatasourceDocument {
	private JSONParser parser = new JSONParser();
	private final static Logger logger = Logger.getLogger(MongoDB.class);
    private MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
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
	
	public void insertString(String collectionName, String value) throws ParseException{

		DBCollection collection = db.getCollection(collectionName);
	    DBObject document=new BasicDBObject();	    
		JSONObject jsonObject = (JSONObject) parser.parse(value);
		
		for(Object field:jsonObject.keySet()){
		    document.put(field.toString(), jsonObject.get(field));
		    logger.debug("Field:"+field+"");
		}
	
	    if(collection!=null){
	    	collection.insert(document);
	    }
	}

	@Override
	public String getDatabaseType() {
		return "MongoDB";
	}

}
