package rendezvous.federator.executor.impl;

import org.json.simple.parser.ParseException;
import org.junit.Test;

public class ExecutorInsertTest {

	@Test
	public void testNullPlanShouldFail() throws ParseException {
		ExecutorImpl executor = new ExecutorImpl();
		executor.getExecute(null);
	}

}
