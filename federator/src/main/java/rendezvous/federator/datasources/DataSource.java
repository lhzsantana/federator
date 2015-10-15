package rendezvous.federator.datasources;

import java.util.Set;

import org.json.simple.parser.ParseException;

import rendezvous.federator.dictionary.Value;

public abstract class DataSource {

	public abstract String getDatabaseType();
	public abstract String getName();
	public abstract void setName(String name);
	public abstract void connect();
	public abstract String insert(String table, String entity, Set<Value> values) throws ParseException;

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof DataSource)) {
			return false;
		}else{			
			DataSource dataSource = (DataSource) obj;
			
			if(dataSource.getName().equals(this.getName())){
				return true;
			}else{
				return false;
			}			
		}
	}
}
