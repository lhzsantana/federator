package rendezvous.federator.dictionary;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.datasources.Datasource;

public interface DictionaryReader {

	public DataElement getDataElement(String element) throws JsonParseException, IOException, Exception;

	public Dictionary refreshDictionary() throws JsonParseException, IOException, Exception;
	
	public Set<Datasource> getDatasources() throws JsonParseException, IOException, Exception;
}
