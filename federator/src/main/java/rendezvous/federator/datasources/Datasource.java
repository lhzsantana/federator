package rendezvous.federator.datasources;

public interface Datasource {

	public String getDatabaseType();
	public String getName();
	public void setName(String name);
	public void connect();
}
