package rendezvous.federator.api;

import java.util.List;

public class GetResponse implements Response {

	private List<Hit> hits;
	
	@Override
	public List<Hit> getHits() {
		return hits;
	}

	public void setHits(List<Hit> hits) {
		this.hits=hits;
	}

}
