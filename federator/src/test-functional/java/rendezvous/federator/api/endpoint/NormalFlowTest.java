package rendezvous.federator.api.endpoint;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.mchange.util.AssertException;

import rendezvous.federator.api.endpoint.impl.DeleteEndpoint;
import rendezvous.federator.api.endpoint.impl.GetEndpointTest;
import rendezvous.federator.api.endpoint.impl.InsertEndpoint;
import rendezvous.federator.api.endpoint.impl.MappingEndpoint;
import rendezvous.federator.api.endpoint.impl.QueryEndpoint;

public class NormalFlowTest {

	final static Logger logger = Logger.getLogger(NormalFlowTest.class);

	MappingEndpoint mappingEndpoint = new MappingEndpoint();
	InsertEndpoint insertEndpoint = new InsertEndpoint();
	DeleteEndpoint deleteEndpoint = new DeleteEndpoint();
	QueryEndpoint queryEndpoint = new QueryEndpoint();
	
	@Test
	public void NormalFlowtest() throws Exception {

		FileInputStream inputStream2 = new FileInputStream("mappings2.yml");
		FileInputStream inputStream3 = new FileInputStream("mappings3.yml");
		
		try {
			
		    String mapping2 = IOUtils.toString(inputStream2);
		    String mapping3 = IOUtils.toString(inputStream3);
			
			mappingEndpoint.put(mapping2);
			
			insertEndpoint.insert("{\"user\":{\"name\":\"luiz\"}}");
			
			try {
				insertEndpoint.insert("{\"user\":{\"address\":\"lagoa\"}}");				
			}catch(Exception e){
				logger.error(e);
			}
			
			mappingEndpoint.put(mapping3);
			
			insertEndpoint.insert("{\"user\":{\"address\":\"lagoa\"}}");	

			queryEndpoint.query("{\"user\":{\"name\":\"joao\"}}");
			
		} finally {
		    inputStream2.close();
		    inputStream3.close();
		}		
	}
}
