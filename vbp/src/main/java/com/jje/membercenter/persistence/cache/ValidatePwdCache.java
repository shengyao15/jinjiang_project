/**
 * 
 */
package com.jje.membercenter.persistence.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Lenvon_Lee
 * 
 */
@Component
public class ValidatePwdCache
{
	@Autowired
	CoherenceCache coherenceCache;

	@Value(value = "${cache.environment}")
	private String environment;

	public void putValueToCache(String telPhone , String validateNum)
	{
		String key = telPhone;
		coherenceCache.put(key, validateNum, this.getCacheName());
	}

	public String getValueFromCache(String telPhone)
	{
		String validateNum = (String) coherenceCache.get(telPhone, this.getCacheName());
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
