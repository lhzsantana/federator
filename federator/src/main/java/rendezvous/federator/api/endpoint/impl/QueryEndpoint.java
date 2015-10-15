package rendezvous.federator.api.endpoint.impl;

import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.api.response.QueryResponse;

public class QueryEndpoint extends Endpoint{

	public QueryResponse query(){
		
		this.refreshGraph();
		
		return null;		
	}
	
	private void refreshGraph(){
		
	}
}
