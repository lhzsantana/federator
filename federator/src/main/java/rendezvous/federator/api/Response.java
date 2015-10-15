package rendezvous.federator.api;

import java.util.Date;

public abstract class Response {
	
	public Response(){
		status=Status.PROCESSING;
		date=new Date();
	}
	
	private Status status;
	private Date date;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
}
