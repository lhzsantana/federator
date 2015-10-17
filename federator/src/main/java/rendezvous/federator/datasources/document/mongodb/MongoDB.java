package rendezvous.federator.datasources.document.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void connect() throws NumberFormatException, Exception {
		
		if (db == null) {			
			mongoClient = new MongoClient(getConfiguration().get("host"),
					Integer.valueOf(getConfiguration().get("port")));
			
			db = mongoClient.getDB(getConfiguration().get("database"));
			collection = db.getCollection(getConfiguration().get("database"));
			logger.info("Connected to " + getDataSourceType());
		}
	}

	public String insert(String collectionName, String entity, Set<Value> values) throws ParseException {

		String id = UUID.randomUUID().toString();

		try {
			// this.connect();

			// db.createCollection(entity, null);
			// DBObject document=new BasicDBObject();
			// document.put("rendezvous_id", id);
			for (Value value : values) {
				// document.put(value.getField(), value.getValue());
				logger.info("Added field " + value.getField() + " and value " + value.getValue());
			}

			logger.info("Inserted the entity " + entity);
		} catch (Exception e) {
			logger.error(e);
		}
		/*
		 * if(collection!=null){ collection.insert(document); }
		 */

		return id;
	}

	@Override
	public String getDataSourceType() {
		return DataSourceType.MONGODB.toString().toLowerCase();
	}

	@Override
	public Hit get(String string, String entity, Set<Value> values) {
		logger.debug("Getting from MongoDB");
		return null;
	}

	@Override
	public List<Hit> query(String string, String entity, Set<Value> values) {

		logger.info("Searching from MongoDB");

		BasicDBObject fields = new BasicDBObject();

		for (Value value : values) {
			logger.debug("The following values <" + value.getValue() + ">");
			fields.put(value.getField(), value.getEntity());
		}

		List<Hit> hits = new ArrayList<Hit>();

		/*
		 * DBCursor cursor = collection.find(allQuery, fields);
		 * 
		 * while (cursor.hasNext()) {
		 * 
		 * hits.add(new Hit()); logger.info(cursor.next()); }
		 */

		return hits;
	}
}
