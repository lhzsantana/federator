package rendezvous.federator.executor.impl;

import org.apache.log4j.Logger;

import rendezvous.federator.api.Response;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;

public class ExecutorImpl implements Executor{

	final static Logger logger = Logger.getLogger(ExecutorImpl.class);
	
	public Response execute(Plan plan) {

		for(Access access: plan.getAccesses()){
			logger.debug(access.getDataElement().getName());
		}		
		
		return null;
	}

}
