package rendezvous.federator.datasources.graph.neo4j;

import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import static org.neo4j.graphdb.DynamicRelationshipType.*;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.graph.DatasourceGraph;

public class Neo4J extends DatasourceGraph {
	
	GraphDatabaseService graphDb;
	
	private static enum RelTypes implements RelationshipType
	{
	    KNOWS
	}
	
	@Override
	public String getDataSourceType() {
		// TODO Auto-generated method stub
		return null;
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
	public boolean connect() throws Exception {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(
		        "target/read-only-db/location" )
                .setConfig( GraphDatabaseSettings.read_only, "true" )
                .newGraphDatabase();
		
		return false;
	}

	@Override
	public String insert(Entity entity, Set<Value> values) throws ParseException, Exception {
		
		Node firstNode = graphDb.createNode();
		firstNode.setProperty( "message", "Hello, " );
		Node secondNode = graphDb.createNode();
		secondNode.setProperty( "message", "World!" );

		Relationship relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS);
		relationship.setProperty( "message", "brave Neo4j " );
				
		return null;
	}

	@Override
	public List<Hit> query(String entity, Set<Value> values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hit get(String entity, String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createDataElements(Entity entity, Set<Field> fields) {
		// TODO Auto-generated method stub

	}

}
