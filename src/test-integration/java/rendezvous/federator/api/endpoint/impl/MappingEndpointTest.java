package rendezvous.federator.api.endpoint.impl;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import rendezvous.federator.api.endpoint.EndpointsTest;

public class MappingEndpointTest extends EndpointsTest {
	
	final static Logger logger = Logger.getLogger(MappingEndpointTest.class);

	MappingEndpoint mappingEndpoint = new MappingEndpoint();

	@Test
	public void testPutMapping() throws Exception {

		FileInputStream inputStream = new FileInputStream("mappings4.yml");
		
	    String mapping = IOUtils.toString(inputStream);
	    
		mappingEndpoint.put(mapping);
	}
	
}
