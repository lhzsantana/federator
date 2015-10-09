package rendezvous.federator.datasources;

public interface Datasource {

	public String getDatabaseType();
	public String getName();
	public void setName(String name);
	public void connect();
	public void insertString(String collection, String field, String value);
}
