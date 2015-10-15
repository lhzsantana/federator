package rendezvous.federator.api.response;

import java.util.List;

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

}
