package rendezvous.federator.executor.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonParseException;

import rendezvous.federator.api.GetResponse;
import rendezvous.federator.api.Hit;
import rendezvous.federator.api.InsertResponse;
import rendezvous.federator.cache.Cache;
import rendezvous.federator.cache.impl.BloomFilterImpl;
import rendezvous.federator.datasources.Datasource;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;

public class ExecutorImpl implements Executor {

	private Cache cache = new BloomFilterImpl();

	public void connectToSources(Set<Datasource> datasources) throws JsonParseException, IOException {
		for (Datasource datasource : datasources) {
			datasource.connect();
		}
	}

	public InsertResponse insertExecute(Plan plan) throws ParseException {
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

	@Override
	public GetResponse getExecute(Plan plan) throws ParseException {
		
		List<Hit> hits=new ArrayList<Hit>();
		
		for (Access access : plan.getAccesses()) {
						
			Hit hit = new Hit();
			hit.setValue(cache.get("1").toString());
		}

		GetResponse response = new GetResponse();
		response.setHits(hits);
		
		return response;
	}
}