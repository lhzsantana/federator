package rendezvous.federator.executor.impl;

import java.io.IOException;
import java.util.Set;

import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.api.Response;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;

public class ExecutorImpl implements Executor {

	public void connectToSources(Set<Datasource> datasources) throws JsonParseException, IOException {
		for (Datasource datasource : datasources) {
			datasource.connect();
		}
	}

	public Response execute(Plan plan) throws ParseException {
		for (Access access : plan.getAccesses()) {
			switch (access.getAction()) {
				case INSERT: {
					(new Thread(new ExecutorInsert(plan))).start();;
					break;
				}
				default: {
					break;
				}
			}
		}

		return null;
	}
}