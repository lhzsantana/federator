package rendezvous.federator.executor;

import rendezvous.federator.api.Response;
import rendezvous.federator.planner.Plan;

public interface Executor {

	public Response execute(Plan plan);
}
