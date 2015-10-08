package rendezvous.federator.canonicalModel;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;

public interface DictionaryReader {

	public DataElement getDataElement(String element) throws JsonParseException, IOException;

}
