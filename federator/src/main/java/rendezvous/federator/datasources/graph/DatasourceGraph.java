package rendezvous.federator.datasources.graph;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.datasources.DataSource;

public abstract class DatasourceGraph extends DataSource {

	protected JSONParser parser = new JSONParser();
}
