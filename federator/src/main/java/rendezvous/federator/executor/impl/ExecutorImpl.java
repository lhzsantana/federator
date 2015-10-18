package rendezvous.federator.executor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import rendezvous.federator.api.endpoint.impl.GetEndpoint;
import rendezvous.federator.api.response.GetResponse;
import rendezvous.federator.api.response.InsertResponse;
import rendezvous.federator.api.response.QueryResponse;
import rendezvous.federator.cache.Cache;
import rendezvous.federator.cache.impl.BloomFilterImpl;
import rendezvous.federator.core.Access;
import rendezvous.federator.core.Action;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Plan;
import rendezvous.federator.core.Value;
import rendezvous.federator.entityManager.EntityManager;
import rendezvous.federator.executor.Executor;
import rendezvous.federator.executor.TransactionManager;

public class ExecutorImpl implements Executor {

	private final static Logger logger = Logger.getLogger(ExecutorImpl.class);

	private final static TransactionManager transactionManager = new TransactionManagerInMemoryImpl();

	private Cache cache = new BloomFilterImpl();

	@Override
	public InsertResponse insertExecute(Plan plan) throws Exception {

		InsertResponse response = new InsertResponse();
		
		Entity entity = this.executeInsert(plan);
		response.setId(entity.getId());
		
		cache.add(entity);
		
		return response;
	}

	@Override
	public GetResponse getExecute(Plan plan) throws Exception {

		List<Hit> hits = this.executeGet(plan);
		
		for(Hit hit:hits){
			cache.add(hit.getEntity());			
		}

		GetResponse response = new GetResponse();
		response.setHits(hits);
		
		return response;
	}

	@Override
	public QueryResponse queryExecute(Plan plan) throws Exception {

		List<Hit> hits = this.executeQuery(plan);

		for(Hit hit:hits){
			cache.add(hit.getEntity());			
		}
		
		QueryResponse response = new QueryResponse();
		response.setHits(hits);

		return response;
	}

	private Entity executeInsert(Plan plan) throws Exception {

		logger.debug("Executing a new plan");

		String entityId = UUID.randomUUID().toString();

		List<Access> accessed = new ArrayList<Access>();
		Set<Value> values = new HashSet<Value>();
		
		for (Access access : plan.getAccesses()) {

			logger.debug("Executing a new access");

			String transactionId = transactionManager.register(plan, access);

			transactionManager.start(transactionId);
			try {
				Entity entity = access.getEntity();
				entity.setId(entityId);
				access.setEntity(entity);
				
				access.getDataSource().insert(access.getEntity(), access.getValues());
				values.addAll(access.getValues());
				
			} catch (ParseException e) {
				logger.debug(e);
			}
			transactionManager.finish(transactionId);

			access.setAction(Action.GET);
			accessed.add(access);
		}

		logger.info("The entity <"+entityId+"> was added to the Federator");
		
		EntityManager.addEntity(entityId, accessed);

		return new Entity(entityId,values);
	}

	private List<Hit> executeGet(Plan plan) throws Exception {

		//TODO: change the String value for a Entity reference
		Map<String, List<Value>> intermediateValues = new HashMap<String, List<Value>>();

		for (Access access : plan.getAccesses()) {

			logger.info("Getting the access plan "+access.getEntity().getName()+" for the datasource "+access.getDataSource().getDataSourceType());
			
			if(access.getDataSource().getDataSourceType().equals("Relationship")){
				
				GetEndpoint get = new GetEndpoint();
			
				List<Value> subValues = new ArrayList<Value>();
				
				for(Value value : access.getValues()){

					logger.info("Getting the relationship "+value.getValue());
					
					GetResponse response = get.get(value.getValue());
										
					for(Hit hit:response.getHits()){
						
						Value subValue = new Value(access.getEntity().getName(), value.getField().getFieldName(), hit.getValues().toString(), value.getType());
						subValues.add(subValue);
					}
				}
				
				intermediateValues.put(access.getEntity().getName(), subValues);
			}else{
				
				Hit hit = access.getDataSource().get(access.getEntity().getName(), access.getEntity().getId());
				
				for(Value value : hit.getValues()){
				
					List<Value> volatileValues = intermediateValues.get(value.getEntity());
					
					if(volatileValues==null) volatileValues=new ArrayList<Value>();				
					volatileValues.add(value);
					
					intermediateValues.put(value.getEntity().getName(), volatileValues);
				}
			}
		}
		
		return this.mergeHits(intermediateValues);
	}

	private List<Hit> executeQuery(Plan plan) throws Exception {

		//TODO: change the String value for a Entity reference
		Map<String, List<Value>> intermediateValues = new HashMap<String, List<Value>>();

		for (Access access : plan.getAccesses()) {
			
			List<Hit> hits = access.getDataSource().query(access.getEntity().getName(), access.getValues());
			
			for(Hit hit : hits){
				for(Value value : hit.getValues()){
					
					List<Value> volatileValues = intermediateValues.get(value.getEntity());
					
					if(volatileValues == null) volatileValues = new ArrayList<Value>();
					volatileValues.add(value);
					
					intermediateValues.put(value.getEntity().getName(), volatileValues);
				}
			}
		}

		return this.mergeHits(intermediateValues);
	}

	private List<Hit> mergeHits(Map<String, List<Value>> intermediateValues) {

		logger.debug("Merging <"+intermediateValues.size()+"> intermediate values");
				
		List<Hit> finalHits = new ArrayList<Hit>();
		Integer relevance = 0;
		
		for(String entity: intermediateValues.keySet()){
			Hit hit = new Hit();
			hit.setValues(intermediateValues.get(entity));
			hit.setRelevance(relevance);
			hit.setEntity(new Entity(entity,hit.getValues()));
			
			finalHits.add(hit);
		}
				
		return finalHits;
	}
}