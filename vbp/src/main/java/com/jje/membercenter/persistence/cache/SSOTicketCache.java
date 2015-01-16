/**
 * 
 */
package com.jje.membercenter.persistence.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.SSOTicketDto;

/**
 * @author SHENGLI_LU
 *
 */
@Component
public class SSOTicketCache {
	@Autowired
	CoherenceCache coherenceCache;

    @Value(value = "${cache.environment}")
	private String environment;


	public void putTicket(SSOTicketDto ticketDto) {
		String key = ticketDto.getTicket();
		coherenceCache.put(key,
                CacheObjectSerializlation.serialize(ticketDto.getMember(),MemberDto.class), this.getCacheName());
	}

	public void removeTicket(String key) {
		coherenceCache.remove(key, this.getCacheName());
	}
	
	public SSOTicketDto getTicket(String key) {
        byte[] dpaByte =  (byte[])coherenceCache.get(key, this.getCacheName());
        if(dpaByte!=null)
        {
        	MemberDto member = CacheObjectSerializlation.deserialize(dpaByte,MemberDto.class);
        	SSOTicketDto ticket = new SSOTicketDto();
        	ticket.setTicket(key);
        	ticket.setMember(member);
			return ticket;
        }
        return null;
	}

    private String getCacheName() {
        return SSOTicketDto.class.getSimpleName() + "_" + environment;
    }

	public Object get(String key, String cacheName) {
		return coherenceCache.get(key, cacheName);
	}
}
