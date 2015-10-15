package rendezvous.federator.executor;

import org.json.simple.parser.ParseException;

import rendezvous.federator.api.GetResponse;
import rendezvous.federator.api.InsertResponse;
import rendezvous.federator.planner.Plan;

public interface Executor {

	public InsertResponse insertExecute(Plan plan) throws ParseException;
	public GetResponse getExecute(Plan plan) throws ParseException;
}
