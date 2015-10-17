package rendezvous.federator.executor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import rendezvous.federator.api.response.GetResponse;
import rendezvous.federator.api.response.InsertResponse;
import rendezvous.federator.api.response.QueryResponse;
import rendezvous.federator.cache.Cache;
import rendezvous.federator.cache.impl.BloomFilterImpl;
import rendezvous.federator.core.Access;
import rendezvous.federator.core.Action;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;
import rendezvous.federator.datasources.DataSource;
import rendezvous.federator.entityManager.EntityManager;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.executor.TransactionManager;

public class ExecutorImpl implements Executor {

	private final static Logger logger = Logger.getLogger(ExecutorImpl.class);

	private final static TransactionManager transactionManager = new TransactionManagerInMemoryImpl();

	private Cache cache = new BloomFilterImpl();

	@Override
	public InsertResponse insertExecute(Plan plan) throws ParseException {

		InsertResponse response = new InsertResponse();
		response.setId(this.executeInsert(plan));

		return response;
	}

	@Override
	public GetResponse getExecute(Plan plan) throws ParseException {

		List<Hit> hits = this.executeGet(plan);

		GetResponse response = new GetResponse();
		response.setHits(hits);

		return response;
	}

	@Override
	public QueryResponse queryExecute(Plan plan) throws ParseException {

		List<Hit> hits = this.executeQuery(plan);

		QueryResponse response = new QueryResponse();
		response.setHits(hits);

		return response;
	}

	private String executeInsert(Plan plan) {

		logger.debug("Executing a new plan");

		String entityId = UUID.randomUUID().toString();

		List<Access> accessed = new ArrayList<Access>();

		for (Access access : plan.getAccesses()) {

			logger.debug("Executing a new access");

			String transactionId = transactionManager.register(plan, access);

			transactionManager.start(transactionId);
			try {
				access.getDataSource().insert("federator", access.getEntity(), access.getValues());
			} catch (ParseException e) {

				logger.debug(e);
			}
			transactionManager.finish(transactionId);

			access.setAction(Action.GET);
			accessed.add(access);
		}

		EntityManager.addEntity(entityId, accessed);

		return entityId;
	}

	private List<Hit> executeGet(Plan plan) {

		//TODO: change the String value for a Entity reference
		Map<String, List<Value>> intermediateValues = new HashMap<String, List<Value>>();

		for (Access access : plan.getAccesses()) {

			Hit hit = access.getDataSource().get("federator", access.getEntity(), access.getValues());
			
			for(Value value : hit.getValues()){
			
				List<Value> volatileValues = intermediateValues.get(value.getEntity());
				volatileValues.add(value);
				
				intermediateValues.put(value.getEntity(), volatileValues);
			}
		}
		
		return this.mergeHits(intermediateValues);
	}

	private List<Hit> executeQuery(Plan plan) {

		//TODO: change the String value for a Entity reference
		Map<String, List<Value>> intermediateValues = new HashMap<String, List<Value>>();

		for (Access access : plan.getAccesses()) {
			
			List<Hit> hits = access.getDataSource().query("federator", access.getEntity(), access.getValues());
			
			for(Hit hit : hits){
				for(Value value : hit.getValues()){
					
					List<Value> volatileValues = intermediateValues.get(value.getEntity());
					volatileValues.add(value);
					
					intermediateValues.put(value.getEntity(), volatileValues);
				}
			}
		}

		return this.mergeHits(intermediateValues);
	}

	private List<Hit> mergeHits(Map<String, List<Value>> intermediateValues) {

		logger.debug("Merging <"+intermediateValues.size()+"> intermediate values");
				
		List<Hit> finalHits = new ArrayList<Hit>();
		Integer relevance = 0;
		
		for(String entity: intermediateValues.keySet()){
			Hit hit = new Hit();
			hit.setValues(intermediateValues.get(entity));
			hit.setRelevance(relevance);
			
			finalHits.add(hit);
		}
				
		return finalHits;
	}
}