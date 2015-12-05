package rendezvous.federator.api.response.impl;

import java.util.Date;

public abstract class Response extends javax.ws.rs.core.Response {
	
	public Response(){
		date=new Date();
	}
	
	private Date date;
	
}
