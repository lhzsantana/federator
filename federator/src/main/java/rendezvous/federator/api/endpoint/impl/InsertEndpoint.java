package rendezvous.federator.api.endpoint.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import rendezvous.federator.api.Response;
import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.planner.Plan;

public class InsertEndpoint extends Endpoint {
	
	final static Logger logger = Logger.getLogger(InsertEndpoint.class);

	public Response insert(String string) throws IOException, ParseException {
		
		super.isJSONValid(string);
		
		//find the data elements
		Set<String> elements = super.extractElements(string);
		
		//find the dataElments related with each new insertion
		Set<DataElement> dataElements = new HashSet<DataElement>();
		
		for(String element:elements){
			logger.debug("The element " + element + " will be inserted");
			
			dataElements.add(dictionary.getDataElement(element));
		}
		
		//create plan
		Plan plan = super.planner.createPlan(dataElements);
		
		//execute plan
		return super.executor.execute(plan);
	}

}
