package rendezvous.federator.executor.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import rendezvous.federator.executor.Transaction;
import rendezvous.federator.executor.TransactionManager;
import rendezvous.federator.executor.TransactionStates;
import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;

public class TransactionManagerInMemoryImpl extends TransactionManager{

	final static Logger logger = Logger.getLogger(TransactionManagerInMemoryImpl.class);
	
	@Override
	public String register(Plan plan, Access access) {
		
	    String transactionId = UUID.randomUUID().toString();
		Transaction currentTransaction = new Transaction();
	    
		currentTransaction.setId(transactionId);
		currentTransaction.setAccess(access);
		currentTransaction.setPlan(plan);
		currentTransaction.setRegistered(new Date());
		currentTransaction.setState(TransactionStates.REGISTERED);
		
		transactions.put(transactionId, currentTransaction);
		
		return transactionId;
	}

	@Override
	public void start(String id) {
		Transaction transaction = transactions.get(id);
		transaction.setState(TransactionStates.STARTED);
		logger.info("The transaction "+id+" started");
	}

	@Override
	public void finish(String id) {
		Transaction transaction = transactions.get(id);
		transaction.setState(TransactionStates.FINISHED);
		logger.info("The transaction "+id+" finished");	
	}

	@Override
	public List<Transaction> getUnfinished() {
		
		List<Transaction> unfinished = new ArrayList<Transaction>();
		
		for(Transaction transaction : transactions.values()){
			if(transaction.getState().equals(TransactionStates.STARTED)){
				unfinished.add(transaction);
			}
		}
		
		return unfinished;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

}
