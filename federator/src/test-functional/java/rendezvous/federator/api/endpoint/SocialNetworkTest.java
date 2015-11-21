package rendezvous.federator.api.endpoint;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import rendezvous.federator.api.endpoint.impl.DeleteEndpoint;
import rendezvous.federator.api.endpoint.impl.InsertEndpoint;
import rendezvous.federator.api.endpoint.impl.MappingEndpoint;
import rendezvous.federator.api.endpoint.impl.QueryEndpoint;
import rendezvous.federator.api.response.QueryResponse;
import rendezvous.federator.core.Hit;

public class SocialNetworkTest {

	final static Logger logger = Logger.getLogger(SocialNetworkTest.class);

	MappingEndpoint mappingEndpoint = new MappingEndpoint();
	InsertEndpoint insertEndpoint = new InsertEndpoint();
	DeleteEndpoint deleteEndpoint = new DeleteEndpoint();
	QueryEndpoint queryEndpoint = new QueryEndpoint();
	
	@Test
	public void NormalFlowtest() throws Exception {

		FileInputStream inputStream = new FileInputStream("mappings4.yml");
		
		try {
			
		    String mapping = IOUtils.toString(inputStream);
		    
			mappingEndpoint.put(mapping);
			
			insertEndpoint.insert("{\"user\":{\"name\":\"luiz\"}}");

			QueryResponse query = queryEndpoint.query("{\"user\":{\"name\":\"luiz\"}}");			
			
			for(Hit hit:query.getHits()){
				logger.info(hit.pretty());
			}
			
			/*
			try {
				insertEndpoint.insert("{\"user\":{\"address\":\"lagoa\"}}");				
			}catch(Exception e){
				logger.error(e);
			}
			
			insertEndpoint.insert("{\"user\":{\"address\":\"lagoa\"}}");
			insertEndpoint.insert("{\"user\":{\"name\":\"luiz\", \"address\":\"lagoa\"}}");
			insertEndpoint.insert("{\"user\":{\"name\":\"joao\",\"address\":\"lagoa\"}}");

			QueryResponse query = queryEndpoint.query("{\"user\":{\"address\":\"lagoa\"}}");			
			
			for(Hit hit:query.getHits()){
				logger.info(hit.pretty());
			}*/
			
		} finally {
		    inputStream.close();
		}		
	}
}
