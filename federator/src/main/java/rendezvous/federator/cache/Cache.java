package rendezvous.federator.cache;

public interface Cache {

	public void expiresAll();
	public Value get(Key key);
}
