package rendezvous.federator.datasources.keyvalue;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.datasources.Datasource;

public abstract class KeyValueDatasource extends Datasource {
	
	protected JSONParser parser = new JSONParser();

}
