package rendezvous.federator.executor.impl;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import rendezvous.federator.executor.Executor;

public class ExecutorInsertTest {

	@Test
	public void testNullPlanShouldFail() throws ParseException {
		Executor executor = new ExecutorImpl();
		executor.getExecute(null);
	}

}
