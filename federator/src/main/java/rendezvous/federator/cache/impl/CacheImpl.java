package rendezvous.federator.cache.impl;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;

public class CacheImpl {
	
	BloomFilter<String> bf = new FilterBuilder(1000, 0.1).buildBloomFilter();
	
}
