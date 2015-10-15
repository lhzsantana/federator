package rendezvous.federator.datasources.document;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.datasources.DataSource;

public abstract class DatasourceDocument extends DataSource {

	protected JSONParser parser = new JSONParser();
}
