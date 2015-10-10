package rendezvous.federator.executor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;

public abstract class TransactionManager {

	final static Logger logger = Logger.getLogger(TransactionManager.class);

	protected static Map<String, Transaction> transactions = new HashMap<String, Transaction>();
	
	public abstract String register(Plan plan, Access access);	
	public abstract void start(String id);
	public abstract void finish(String id);

	public abstract List<Transaction> getUnfinished();
	public abstract void flush();
	
}
