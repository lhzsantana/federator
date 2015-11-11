package rendezvous.federator.api.endpoint;

import org.junit.Test;

import rendezvous.federator.api.endpoint.impl.DeleteEndpoint;
import rendezvous.federator.api.endpoint.impl.InsertEndpoint;
import rendezvous.federator.api.endpoint.impl.MappingEndpoint;
import rendezvous.federator.api.endpoint.impl.QueryEndpoint;

public class NormalFlowTest {

	MappingEndpoint mappingEndpoint = new MappingEndpoint();
	InsertEndpoint insertEndpoint = new InsertEndpoint();
	DeleteEndpoint deleteEndpoint = new DeleteEndpoint();
	QueryEndpoint queryEndpoint = new QueryEndpoint();
	
	@Test
	public void NormalFlowtest() throws Exception {

		mappingEndpoint.put("{}");
		
		insertEndpoint.insert("{'name':'luiz'}");
		
		queryEndpoint.query("{'name':'luiz'}");
		
	}
}
