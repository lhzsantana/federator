package rendezvous.federator.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Plan {
	
	private Date createdAt = new Date();
	private List<Access> accesses = new ArrayList<Access>();
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public List<Access> getAccesses() {
		return accesses;
	}
	public void setAccesses(List<Access> accesses) {
		this.accesses = accesses;
	}	
}
