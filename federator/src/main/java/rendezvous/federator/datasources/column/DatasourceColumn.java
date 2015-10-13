package rendezvous.federator.datasources.column;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.datasources.Datasource;

public abstract class DatasourceColumn implements Datasource {

	protected JSONParser parser = new JSONParser();
}
