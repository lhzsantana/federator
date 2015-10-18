package rendezvous.federator.cache.impl;

import java.util.List;

import org.apache.log4j.Logger;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;
import rendezvous.federator.cache.Cache;
import rendezvous.federator.core.Entity;
import rendezvous.federator.core.Field;
import rendezvous.federator.core.Hit;
import rendezvous.federator.core.Value;

public class BloomFilterImpl implements Cache {

	BloomFilter<String> bf = new FilterBuilder(1000, 0.1).buildBloomFilter();

	private final static Logger logger = Logger.getLogger(BloomFilterImpl.class);
	
	@Override
	public List<Hit> get(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hit> get(Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Entity entity) {
		
		for(Value value:entity.getValues()){
			logger.info(value.getField().getFieldName());
		}
	}

	@Override
	public void add(Value value) {
		// TODO Auto-generated method stub
		
	}
}
