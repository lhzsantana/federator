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
import rendezvous.federator.core.Value;

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
		FileInputStream inputStream4 = new FileInputStream("mappings4.yml");
		
		try {
			
		    String mapping2 = IOUtils.toString(inputStream2);
		    String mapping3 = IOUtils.toString(inputStream3);
		    String mapping4 = IOUtils.toString(inputStream4);
			/*
			mappingEndpoint.put(mapping2);
			
			insertEndpoint.insert("{\"user\":{\"name\":\"luiz\"}}");
			
			try {
				insertEndpoint.insert("{\"user\":{\"address\":\"lagoa\"}}");				
			}catch(Exception e){
				logger.error(e);
			}
			
			mappingEndpoint.put(mapping3);
			
			insertEndpoint.insert("{\"user\":{\"address\":\"lagoa\"}}");

			logger.info("----------------------------------------");
			*/
		    
			mappingEndpoint.put(mapping4);
			
			insertEndpoint.insert("{\"user\":{\"name\":\"luiz\", \"address\":\"lagoa\"}}");
			insertEndpoint.insert("{\"user\":{\"name\":\"joao\",\"address\":\"lagoa\"}}");

			QueryResponse query = queryEndpoint.query("{\"user\":{\"address\":\"lagoa\"}}");			
			
			for(Hit hit:query.getHits()){
				logger.info(hit.pretty());
			}
			
		} finally {
		    inputStream2.close();
		    inputStream3.close();
		}		
	}
}
