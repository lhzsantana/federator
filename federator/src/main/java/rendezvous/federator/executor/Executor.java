package rendezvous.federator.executor;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.api.Response;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.planner.Plan;

public interface Executor {

	public void connectToSources(Set<Datasource> datasources) throws JsonParseException, IOException;
	public Response execute(Plan plan);
}
