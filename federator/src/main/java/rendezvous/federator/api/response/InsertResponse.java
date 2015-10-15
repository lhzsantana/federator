package rendezvous.federator.api.response;

import rendezvous.federator.api.response.impl.Response;

public class InsertResponse extends Response{

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
