package rendezvous.federator.executor;

import org.json.simple.parser.ParseException;

import rendezvous.federator.api.response.GetResponse;
import rendezvous.federator.api.response.InsertResponse;
import rendezvous.federator.api.response.QueryResponse;
import rendezvous.federator.core.Plan;

public interface Executor {

	public InsertResponse insertExecute(Plan plan) throws Exception;
	public GetResponse getExecute(Plan plan) throws Exception;
	public QueryResponse queryExecute(Plan plan) throws ParseException, Exception;
}
