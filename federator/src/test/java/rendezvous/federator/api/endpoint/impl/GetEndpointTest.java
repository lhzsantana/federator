package rendezvous.federator.api.endpoint.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.junit.Test;

import rendezvous.federator.api.endpoint.EndpointTest;
import rendezvous.federator.api.response.DeleteResponse;
import rendezvous.federator.api.response.GetResponse;
import rendezvous.federator.api.response.InsertResponse;
import rendezvous.federator.api.response.impl.Status;

public class GetEndpointTest extends EndpointTest {
	
	final static Logger logger = Logger.getLogger(GetEndpointTest.class);

	@Test
	public void testGetUnexistent() throws Exception {

		DeleteEndpoint deleteEndpoint = new DeleteEndpoint();
		DeleteResponse response = deleteEndpoint.delete("12345");
		assertEquals(Status.SUCCESS,response.getStatus());
				
		GetEndpoint getEndpoint = new GetEndpoint();
		GetResponse getResponse = getEndpoint.get("12345");
		assertNull(getResponse.getHits());
	}
	
	@Test
	public void testInsertGetRelation() throws Exception {
		
		InsertEndpoint insertEndpoint = new InsertEndpoint();

		InsertResponse insertResponse = insertEndpoint.insert("{\"usuarios1\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		
		logger.info("The insert response has the id <"+insertResponse.getId()+">");
		
		insertResponse = insertEndpoint.insert("{\"posts1\":{\"body\":\"TESTE BODY 1\",\"author\":\""+insertResponse.getId()+"\"}}");
		
		logger.info("The insert response has the id <"+insertResponse.getId()+">");

		GetEndpoint getEndpoint = new GetEndpoint();
		GetResponse response = getEndpoint.get(insertResponse.getId());

		printHits(response.getHits());
	}	

	@Test
	public void testInsertGet() throws Exception {

		InsertEndpoint insertEndpoint = new InsertEndpoint();
		InsertResponse insertResponse = insertEndpoint.insert("{\"usuarios1\":{\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		
		logger.debug("The insert response has the id <"+insertResponse.getId()+">");
		
		insertResponse = insertEndpoint.insert("{\"usuarios1\":{\"email\":\"lhzsantana@gmail.com\",\"username\":\"luiz\",\"password\":\"luiz\",\"address\":\"luiz\"}}");
		
		logger.debug("The insert response has the id <"+insertResponse.getId()+">");
		
		insertResponse = insertEndpoint.insert("{\"posts1\":{\"body\":\"TESTE BODY 1\"}}");
		
		logger.debug("The insert response has the id <"+insertResponse.getId()+">");
				
		GetEndpoint getEndpoint = new GetEndpoint();
		GetResponse response = getEndpoint.get(insertResponse.getId());

		assertNotNull(response.getHits());
		
		printHits(response.getHits());
	}
}
