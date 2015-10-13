package rendezvous.federator.cache.impl;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;
import rendezvous.federator.cache.Cache;
import rendezvous.federator.cache.Key;
import rendezvous.federator.cache.Value;

public class BloomFilterImpl implements Cache {
	
	BloomFilter<String> bf = new FilterBuilder(1000, 0.1).buildBloomFilter();

	@Override
	public void expiresAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Value get(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value get(Integer key) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
