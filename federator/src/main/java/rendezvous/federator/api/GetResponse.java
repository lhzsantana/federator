package rendezvous.federator.api;

import java.util.List;

public class GetResponse extends Response {

	private List<Hit> hits;
	
	public List<Hit> getHits() {
		return hits;
	}

	public void setHits(List<Hit> hits) {
		this.hits=hits;
	}

}
