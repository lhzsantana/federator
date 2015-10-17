package rendezvous.federator.core;

import java.util.List;

public class Hit {

	private List<Value> values;
	private Integer relevance;

	public List<Value> getValues() {
		return values;
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

	public Integer getRelevance() {
		return relevance;
	}

	public void setRelevance(Integer relevance) {
		this.relevance = relevance;
	}
}
