package rendezvous.federator.datasources.graph.neo4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import rendezvous.federator.canonicalModel.DataType;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Type;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSourceType;
import rendezvous.federator.datasources.graph.DatasourceGraph;

public class Neo4J extends DatasourceGraph {

	private final static Logger logger = Logger.getLogger(Neo4J.class);

	private GraphDatabaseService graphDb = null;

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
		if (graphDb == null) {
			graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(getConfiguration().get("neo4j.path"))
					.newGraphDatabase();
		}
	}

	@Override
	public String insert(Entity entity, Set<Value> values) throws ParseException, Exception {

		try (Transaction tx = graphDb.beginTx()) {

			if (values == null || values.isEmpty() || values.size() == 0)
				throw new Exception("Cannot insert an empty list");

			Node node = graphDb.createNode();
			node.addLabel(DynamicLabel.label(entity.getId()));
			node.addLabel(DynamicLabel.label(entity.getName()));

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
	public List<Hit> query(Entity entity, Set<Value> values) throws Exception {

		List<Hit> hits = new ArrayList<Hit>();

		try (Transaction tx = graphDb.beginTx()) {

			for (Value queryValue : values) {

				ResourceIterator<Node> nodes = graphDb.findNodes(DynamicLabel.label(entity.getName()),
						queryValue.getField().getFieldName(), queryValue.getValue());

				while (nodes.hasNext()) {

					Node node = nodes.next();

					List<Value> tempValues = new ArrayList<Value>();

					for (String property : node.getAllProperties().keySet()) {
						Value value = new Value(entity.getName(), property,
								node.getAllProperties().get(property).toString(), new Type(DataType.STRING.toString()));
						tempValues.add(value);
					}

					Hit hit = new Hit();
					hit.setEntity(entity);
					hit.setValues(tempValues);

					hits.add(hit);
				}
			}
		}

		return hits;
	}

	@Override
	public Hit get(Entity entity) throws Exception {

		try (Transaction tx = graphDb.beginTx()) {

			ResourceIterator<Node> nodes = graphDb.findNodes(DynamicLabel.label(entity.getId()));

			List<Hit> hits = new ArrayList<Hit>();

			while (nodes.hasNext()) {

				Node node = nodes.next();

				List<Value> tempValues = new ArrayList<Value>();

				for (String property : node.getAllProperties().keySet()) {
					Value value = new Value(entity.getName(), property,
							node.getAllProperties().get(property).toString(), new Type(DataType.STRING.toString()));
					tempValues.add(value);

					logger.info("Field " + value.getField().getFieldName() + " value " + value.getValue());
				}

				Hit hit = new Hit();
				hit.setEntity(entity);
				hit.setValues(tempValues);

				logger.info("Entity " + entity.getName() + " id " + entity.getId());

				hits.add(hit);
			}

			if (hits.size() > 1)
				throw new Exception("More than one entity was found, something is inconsistent");

			return hits.get(0);
		}
	}

	@Override
	public void close() {
		graphDb.shutdown();
	}

}
