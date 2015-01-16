/**
 * 
 */
package com.jje.membercenter.persistence.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

/**
 * @author SHENGLI_LU
 *
 */
@Component
public class CoherenceCache {

	@Value(value = "${cache.single_mode}")
	private Boolean singleMode;

	@PostConstruct
	public void init() {
		if (singleMode) {
			System.setProperty("tangosol.coherence.ttl", "0");
			System.setProperty("tangosol.coherence.localhost", "127.0.0.1");
		} else {
            CacheFactory.ensureCluster();
			System.setProperty("tangosol.coherence.distributed.localstorage",
					"false");
		
		}
	}

	public NamedCache getCache(String cacheName) {
		return CacheFactory.getCache(cacheName);
	}

	public void put(Object key, Object value, String cacheName) {
		getCache(cacheName).put(key, value);
	}

	public void putAll(Map<String, Object> map, String cacheName) {
		getCache(cacheName).putAll(map);
	}

	public void remove(Object key, String cacheName) {
		getCache(cacheName).remove(key);
	}

	@SuppressWarnings("rawtypes")
	public Map get(Collection keys, String cacheName) {
		try{
			return this.getCache(cacheName).getAll(keys);
		}catch(Exception e){
			return new HashMap();
		}
	}

	public Object get(String key, String cacheName) {
		return this.getCache(cacheName).get(key);
	}

	public void clearCache(String cacheName) {
		this.getCache(cacheName).clear();
	}

	@SuppressWarnings("unchecked")
	public Set<String> getAllCacheKey(String cacheName) {
		return this.getCache(cacheName).keySet();
	}
}
