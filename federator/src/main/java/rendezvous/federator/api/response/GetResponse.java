package rendezvous.federator.api.response;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import rendezvous.federator.api.response.impl.Response;
import rendezvous.federator.core.Hit;

public class GetResponse extends Response {

	private List<Hit> hits;
	
	public List<Hit> getHits() {
		return hits;
	}

	public void setHits(List<Hit> hits) {
		this.hits=hits;
	}

	@Override
	public Object getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultivaluedMap<String, Object> getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

}
