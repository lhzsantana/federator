package rendezvous.federator.api.endpoint;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InsertParam {
	@XmlElement
	public String entity;
}
