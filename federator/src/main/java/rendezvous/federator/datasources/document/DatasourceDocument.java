package rendezvous.federator.datasources.document;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.datasources.Datasource;

public abstract class DatasourceDocument implements Datasource {

	protected JSONParser parser = new JSONParser();
}
