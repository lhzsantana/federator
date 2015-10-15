package rendezvous.federator.cache.impl;

import java.util.List;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;
import rendezvous.federator.cache.Cache;
import rendezvous.federator.cache.Key;
import rendezvous.federator.core.Hit;

public class BloomFilterImpl implements Cache {

	BloomFilter<String> bf = new FilterBuilder(1000, 0.1).buildBloomFilter();

	@Override
	public void expiresAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Hit> get(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hit> get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Hit> get(Integer key) {
		// TODO Auto-generated method stub
		return null;
	}

}
