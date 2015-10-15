package rendezvous.federator.datasources.column;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.datasources.DataSource;

public abstract class DatasourceColumn extends DataSource {

	protected JSONParser parser = new JSONParser();
}
