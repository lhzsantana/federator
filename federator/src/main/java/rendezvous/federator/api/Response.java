package rendezvous.federator.api;

import java.util.List;

public interface Response {

	public List<Hit> getHits();
	public void setHits(List<Hit> hits);
}
