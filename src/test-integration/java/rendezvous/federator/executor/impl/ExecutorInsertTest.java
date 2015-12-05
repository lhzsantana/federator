package rendezvous.federator.executor.impl;

import org.junit.Test;

import rendezvous.federator.executor.Executor;

public class ExecutorInsertTest {

	@Test
	public void testNullPlanShouldFail() throws Exception {
		Executor executor = new ExecutorImpl();
		executor.getExecute(null);
	}

}
