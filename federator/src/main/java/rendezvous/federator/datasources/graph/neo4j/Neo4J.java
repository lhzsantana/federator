package rendezvous.federator.datasources.graph.neo4j;

import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSourceType;
import rendezvous.federator.datasources.graph.DatasourceGraph;

public class Neo4J extends DatasourceGraph {

	GraphDatabaseService graphDb;

	public Neo4J() throws Exception {
		this.connect();
	}

	@Override
	public String getDataSourceType() {
		return DataSourceType.NEO4J.toString().toLowerCase();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connect() throws Exception {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder("target/read-only-db/location").newGraphDatabase();
	}

	@Override
	public String insert(Entity entity, Set<Value> values) throws ParseException, Exception {

		try (Transaction tx = graphDb.beginTx()) {
			
			if (values == null || values.isEmpty() || values.size() == 0)
				throw new Exception("Cannot insert an empty list");

			Node node = graphDb.createNode();

			for (Value value : values) {
				node.setProperty(value.getField().getFieldName(), value.getValue());

				if (value.getType().equals("entity")) {

					Node related = graphDb.getNodeById(Long.valueOf(value.getValue()));

					node.createRelationshipTo(related,
							DynamicRelationshipType.withName(value.getField().getFieldName()));
				}
			}
			
			tx.success();

			return entity.getId();
		}
	}

	@Override
	public List<Hit> query(String entity, Set<Value> values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hit get(Entity entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		graphDb.shutdown();
	}

}
