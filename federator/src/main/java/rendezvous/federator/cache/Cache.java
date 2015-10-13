package rendezvous.federator.cache;

public interface Cache {

	public void expiresAll();
	public Value get(Key key);
	public Value get(String key);
	public Value get(Integer key);
}
