package rendezvous.federator.executor;

import java.util.Date;

import rendezvous.federator.planner.Access;
import rendezvous.federator.planner.Plan;

public class Transaction {

	private String id;
	private Date registered;
	private Date started;
	private Date finished;
	private Plan plan;
	private Access access;
	private TransactionStates state;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getRegistered() {
		return registered;
	}
	public void setRegistered(Date registered) {
		this.registered = registered;
	}
	public Date getStarted() {
		return started;
	}
	public void setStarted(Date started) {
		this.started = started;
	}
	public Date getFinished() {
		return finished;
	}
	public void setFinished(Date finished) {
		this.finished = finished;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public Access getAccess() {
		return access;
	}
	public void setAccess(Access access) {
		this.access = access;
	}
	public TransactionStates getState() {
		return state;
	}
	public void setState(TransactionStates state) {
		this.state = state;
	}	
}
