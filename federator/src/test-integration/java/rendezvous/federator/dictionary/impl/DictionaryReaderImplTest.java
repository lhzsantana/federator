package rendezvous.federator.dictionary.impl;

import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import rendezvous.federator.dictionary.DictionaryReader;

public class DictionaryReaderImplTest {

	private final static Logger logger = Logger.getLogger(DictionaryReaderImplTest.class);
	
	@Test
	public void test() throws Exception {
		
		FileInputStream inputStream = new FileInputStream("mappings4.yml");
		String mapping = IOUtils.toString(inputStream);

		Long start = System.currentTimeMillis();
		
		DictionaryReader reader = new DictionaryReaderImpl();
		reader.refreshDictionary(mapping);

		Long total = System.currentTimeMillis()-start;
		
		logger.info("The test took <"+total+" ms>");
		
	}

}
