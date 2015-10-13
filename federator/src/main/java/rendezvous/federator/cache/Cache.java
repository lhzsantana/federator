package rendezvous.federator.cache;

import java.util.List;

import rendezvous.federator.api.Hit;

public interface Cache {

	public void expiresAll();
	public List<Hit>  get(Key key);
	public List<Hit> get(String key);
	public List<Hit> get(Integer key);
}
