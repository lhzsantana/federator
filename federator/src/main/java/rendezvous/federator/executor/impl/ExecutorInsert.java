package rendezvous.federator.executor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import rendezvous.federator.dictionary.EntityManager;
import rendezvous.federator.executor.TransactionManager;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;

public class ExecutorInsert implements Runnable {

	private final static Logger logger = Logger.getLogger(ExecutorInsert.class);
	private final static TransactionManager transactionManager = new TransactionManagerInMemoryImpl();

	private Plan plan;
	
	public ExecutorInsert(Plan plan){
		this.plan=plan;
	}
	
	@Override
	public void run() {
		
		logger.debug("Executing a new plan");

		String entityId = UUID.randomUUID().toString();		
		List<String> entityIdList = new ArrayList<String>();
		for (Access access : plan.getAccesses()) {

			logger.debug("Executing a new access");

			String transactionId = transactionManager.register(plan, access);

			transactionManager.start(transactionId);
			try {
				entityIdList.add(access.getDataSource().insert("federator", access.getEntity(), access.getValues()));
			} catch (ParseException e) {
				logger.debug(e);
			}
			transactionManager.finish(transactionId);
		}
		
		EntityManager.addEntity(entityId, entityIdList);
	}
}
