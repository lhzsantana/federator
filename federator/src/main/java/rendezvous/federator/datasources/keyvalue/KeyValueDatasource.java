package rendezvous.federator.datasources.keyvalue;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.datasources.DataSource;

public abstract class KeyValueDatasource extends DataSource {
	
	protected JSONParser parser = new JSONParser();

}
