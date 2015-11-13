package rendezvous.federator.dictionary.impl;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import rendezvous.federator.dictionary.DictionaryReader;

public class DictionaryReaderImplTest {

	@Test
	public void test() throws Exception {
		
		FileInputStream inputStream = new FileInputStream("mappings4.yml");
		String mapping = IOUtils.toString(inputStream);
		
		DictionaryReader reader = new DictionaryReaderImpl();
		reader.refreshDictionary(mapping);
	}

}
