package rendezvous.federator.datasources;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;

public abstract class Datasource {

	private final static Logger logger = Logger.getLogger(Datasource.class);
	
	private static Configuration configurations = null;
	
	public abstract String getDataSourceType();
	public abstract String getName();
	public abstract void close();
	public abstract void setName(String name);
	public abstract void connect() throws Exception;	
	public abstract String insert(Entity entity, Set<Value> values) throws ParseException, Exception;
	public abstract Hit get(Entity entity) throws Exception;
	
    @Override
    public int hashCode() {
    	return getDataSourceType().hashCode();
    }

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof Datasource)) {
			return false;
		}else{			
			Datasource dataSource = (Datasource) obj;
			
			if(dataSource.getName().equals(this.getName())){
				return true;
			}else{
				return false;
			}			
		}
	}
	
	protected Map<String, String> getConfiguration() throws Exception{

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

		if(configurations==null){
			configurations = mapper.readValue(new File("datasources.yml"), Configuration.class);
		}
		
		Map<String, String> configuration = new HashMap<String, String>();
		
		for(String key : configurations.getDatasources().get(getDataSourceType()).keySet()){
						
			String value = configurations.getDatasources().get(getDataSourceType()).get(key);
			
			configuration.put(key, value);
			
			logger.info("Parameter <"+key+"> with the value <"+value+"> for the database <"+getDataSourceType()+">");
		}
		
		return configuration;
	}
}
