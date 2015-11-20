package rendezvous.federator.dictionary;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.dictionary.impl.DictionaryReaderImpl;

public class DictionaryReaderImplTest {

	private final static Logger logger = Logger.getLogger(DictionaryReaderImplTest.class);

	@Test
	public void testGetDatasources() throws JsonParseException, IOException, Exception {
		DictionaryReader reader = new DictionaryReaderImpl();

		FileInputStream inputStream = new FileInputStream("mappings4.yml");
		String mapping = IOUtils.toString(inputStream);
		
		reader.refreshDictionary(mapping);
	}
}
