package rendezvous.federator.executor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import rendezvous.federator.api.GetResponse;
import rendezvous.federator.api.Hit;
import rendezvous.federator.api.InsertResponse;
import rendezvous.federator.cache.Cache;
import rendezvous.federator.cache.impl.BloomFilterImpl;
import rendezvous.federator.entityManager.EntityManager;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.executor.TransactionManager;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Action;
import rendezvous.federator.planner.Plan;

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

		logger.debug("Executing a new plan");

		List<Hit> intermediateHits = new ArrayList<Hit>();

		for (Access access : plan.getAccesses()) {

			logger.debug("Executing a new access");

			intermediateHits.add(access.getDataSource().get("federator", access.getEntity(), access.getValues()));
		}

		return this.mergeHits(intermediateHits);
	}

	private List<Hit> mergeHits(List<Hit> intermediateHits) {
		// TODO Auto-generated method stub
		return null;
	}
}