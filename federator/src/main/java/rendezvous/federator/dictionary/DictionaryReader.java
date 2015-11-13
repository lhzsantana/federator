package rendezvous.federator.dictionary;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.core.Field;
import rendezvous.federator.datasources.Datasource;

public interface DictionaryReader {

	public Set<Datasource> getDatasources(Field field) throws JsonParseException, IOException, Exception;

	public Set<Field> getFields() throws Exception;

	public List<String> getTypes(Field field);

	void refreshDictionary(String newMapping) throws Exception;

}
