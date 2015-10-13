package rendezvous.federator.datasources;

import org.json.simple.parser.ParseException;

public interface Datasource {

	public String getDatabaseType();
	public String getName();
	public void setName(String name);
	public void connect();
	public void insertString(String table, String entity, String value) throws ParseException;
}
