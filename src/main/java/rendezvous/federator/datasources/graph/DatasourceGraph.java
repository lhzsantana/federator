package rendezvous.federator.datasources.graph;

import org.json.simple.parser.JSONParser;

import rendezvous.federator.datasources.QueryableDatasource;

public abstract class DatasourceGraph extends QueryableDatasource {

	protected JSONParser parser = new JSONParser();
}
