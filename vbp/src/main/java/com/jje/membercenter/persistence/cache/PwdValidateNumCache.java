/**
 * 
 */
package com.jje.membercenter.persistence.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author SHENGLI_LU
 * 
 */
@Component
public class PwdValidateNumCache
{
	@Autowired
	CoherenceCache coherenceCache;

	@Value(value = "${cache.environment}")
	private String environment;

	public void putValueToCache(String validateNum, String memberCode)
	{
		String key = memberCode;
		coherenceCache.put(key, validateNum, this.getCacheName());
	}

	public String getValueFromCache(String memberCode)
	{
		String validateNum = (String) coherenceCache.get(memberCode, this.getCacheName());
		return validateNum;
	}

	private String getCacheName()
	{
		return this.getClass().getSimpleName() + "_" + environment;
	}

	public Object get(String key, String cacheName)
	{
		return coherenceCache.get(key, cacheName);
	}
}
