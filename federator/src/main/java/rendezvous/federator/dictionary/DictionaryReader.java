package rendezvous.federator.dictionary;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.dictionary.impl.Field;

public interface DictionaryReader {

	public Set<DataSource> getDatasources(Field field) throws JsonParseException, IOException, Exception;

	public Set<Field> getFields() throws Exception;
	
}
