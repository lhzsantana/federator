package rendezvous.federator.executor.cache.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Value;
import rendezvous.federator.executor.cache.Cache;
import rendezvous.federator.executor.cache.CachedValue;

public class BloomFilterImpl implements Cache {

	private final static int maxSize = 10000;
	private static long currentSize = 0;
	private static double evictionLevel = 0.999;
	private static double cleaningLevel = 0.9;
	
	private static BloomFilter<Field>  bf = new FilterBuilder(maxSize, 0.1).buildBloomFilter();
	private static BloomFilter<Entity>  bfEntities = new FilterBuilder(maxSize, 0.1).buildBloomFilter();
	private static Map<Field,CachedValue> values = new HashMap<Field,CachedValue>();

	private final static Logger logger = Logger.getLogger(BloomFilterImpl.class);

	@Override
	public Set<Value> get(Entity entity) {
		
		Set<Value> values = new HashSet<Value>();
		
		for (Value value : entity.getValues()) {
			values.add(this.get(value.getField()));
			
			logger.debug("Getting value from cache "+value.getField().getFieldName());
		}
		
		return values;
	}

	@Override
	public Value get(Field field) {
		
		if(bf.contains(field)){
			return values.get(field).getValue();
		}
		
		return null;
	}

	@Override
	public void add(Entity entity) {

		logger.info("Adding entity <"+entity.getName()+"> to cache ");
		
		if(entity.isComplete()) bfEntities.add(entity);
		
		for (Value value : entity.getValues()) {
			add(value);
		}
	}

	private void evict() {

		logger.info("Starting eviction");
		
		BloomFilter<Field>  newBF = new FilterBuilder(maxSize, 0.1).buildBloomFilter();
		Map<Field,CachedValue> newValues = new HashMap<Field,CachedValue>();
		
		Long newCurrentSize = (long) 0;
		for(CachedValue value : values.values()){

			if(value.getCachedSequence()>(currentSize-(maxSize*cleaningLevel))){
				newBF.add(value.getValue().getField());
				newValues.put(value.getValue().getField(), value);
				newCurrentSize++;
			}
		}
		
		bf=newBF;
		values=newValues;
		currentSize=newCurrentSize;
		
		System.gc();
		logger.info("Finished eviction");	
	}

	@Override
	public void add(Value value) {

		if (currentSize > Math.round(maxSize*evictionLevel)) evict();
		
		Long cachedSequence = (long) values.size()+1;
		
		CachedValue cachedValue = new CachedValue(value, cachedSequence);
		
		bf.add(value.getField());		
		values.put(value.getField(), cachedValue);
		
		currentSize++;
		logger.info("Current size "+ currentSize);
	}

	@Override
	public void evictAll() {
		bf.clear();		
	}

	@Override
	public boolean contains(Entity entity) {
		
		if(entity.isComplete() && bfEntities.contains(entity)){
			return true;
		}
		
		return false;
	}
}
