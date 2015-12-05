package rendezvous.federator.api.endpoint.impl;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;

import rendezvous.federator.api.endpoint.EndpointTest;
import rendezvous.federator.api.response.InsertResponse;
import rendezvous.federator.api.response.QueryResponse;

public class QueryEndpointTest extends EndpointTest{
	
	final static Logger logger = Logger.getLogger(QueryEndpointTest.class);

	@Test
	public void testInsertQuery() throws Exception {

		InsertEndpoint insertEndpoint = new InsertEndpoint();
		InsertResponse insertResponse1 = insertEndpoint.insert("{\"usuario99\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"lagoa\"}}");
		
		logger.debug("The insert response has the id <"+insertResponse1.getId()+">");
		
		InsertResponse insertResponse2 = insertEndpoint.insert("{\"usuario99\":{\"username\":\"leticia\",\"password\":\"leticia\",\"address\":\"lagoa\"}}");

		logger.debug("The insert response has the id <"+insertResponse2.getId()+">");
					
		QueryEndpoint getEndpoint = new QueryEndpoint();
		
		QueryResponse response = getEndpoint.query("{\"usuario99\":{\"address\":\"lagoa\"}}");
		
		assertNotNull(response.getHits());
		
		printHits(response.getHits());
	}	
}
