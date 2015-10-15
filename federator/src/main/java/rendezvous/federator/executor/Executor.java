package rendezvous.federator.executor;

import org.json.simple.parser.ParseException;

import rendezvous.federator.api.response.GetResponse;
import rendezvous.federator.api.response.InsertResponse;
import rendezvous.federator.core.Plan;

public interface Executor {

	public InsertResponse insertExecute(Plan plan) throws ParseException;
	public GetResponse getExecute(Plan plan) throws ParseException;
}
