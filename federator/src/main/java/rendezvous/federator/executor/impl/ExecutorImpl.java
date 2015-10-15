package rendezvous.federator.executor.impl;

import java.util.List;

import org.json.simple.parser.ParseException;

import rendezvous.federator.api.GetResponse;
import rendezvous.federator.api.Hit;
import rendezvous.federator.api.InsertResponse;
import rendezvous.federator.cache.Cache;
import rendezvous.federator.cache.impl.BloomFilterImpl;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;

public class ExecutorImpl implements Executor {

	private Cache cache = new BloomFilterImpl();

	
	public InsertResponse insertExecute(Plan plan) throws ParseException {

		(new Thread(new ExecutorInsert(plan))).start();

		
		return null;
	}

	@Override
	public GetResponse getExecute(Plan plan) throws ParseException {

		List<Hit> hits = cache.get("");

		if (hits == null) {
			for (Access access : plan.getAccesses()) {

				Hit hit = new Hit();
				hit.setValue(cache.get("1").toString());
			}
		}

		
		GetResponse response = new GetResponse();
		response.setHits(hits);
		
		return response;
	}
}