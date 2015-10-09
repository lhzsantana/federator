package rendezvous.federator.executor.impl;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.api.Response;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;

public class ExecutorImpl implements Executor {

	private final static Logger logger = Logger.getLogger(ExecutorImpl.class);

	public void connectToSources(Set<Datasource> datasources) throws JsonParseException, IOException {

		for (Datasource datasource : datasources) {
			datasource.connect();
		}
	}

	public Response execute(Plan plan) {

		for (Access access : plan.getAccesses()) {
			logger.debug("Executing the plan "+access);
		}
		
		return null;
	}
}