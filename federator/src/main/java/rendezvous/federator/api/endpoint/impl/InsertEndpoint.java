package rendezvous.federator.api.endpoint.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.simple.parser.ParseException;

import rendezvous.federator.api.Response;
import rendezvous.federator.api.endpoint.Endpoint;
import rendezvous.federator.canonicalModel.DataElement;
import rendezvous.federator.canonicalModel.DataSource;
import rendezvous.federator.planner.Plan;

public class InsertEndpoint extends Endpoint {

	public Response insert(String string) throws JsonParseException, JsonMappingException, IOException, ParseException {
		
		super.isJSONValid(string);
		
		//find the data elements
		Set<DataElement> dataElements = super.extractDataElements(string);
		
		//find the datasources related to the insert
		Set<DataSource> dataSources = new HashSet<DataSource>();
		
		for(DataElement dataElement:dataElements){
			dataSources.add(dictionary.getDataSource(dataElement));
		}
		
		//create plan
		Plan plan = super.planner.createPlan(dataElements, dataSources);
		
		//execute plan
		return super.executor.execute(plan);
	}

}
