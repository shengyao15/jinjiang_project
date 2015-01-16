package com.jje.membercenter.persistence.cache;

import java.util.UUID;

import com.jje.membercenter.DataPrepareFramework;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.membercenter.MemberDto;
import com.jje.dto.membercenter.SSOTicketDto;


@Transactional
public class SSOTicketCacheTest extends DataPrepareFramework {

	@Autowired
	SSOTicketCache ticketCache;
	
	@Test
	public void testPutTicket() {
		SSOTicketDto dto = new SSOTicketDto();
        String key = UUID.randomUUID().toString();
		dto.setTicket(key);
		MemberDto memDto = new MemberDto();
		dto.setMember(memDto);
		ticketCache.putTicket(dto);
        SSOTicketDto ssoTicketDto = ticketCache.getTicket(key);
        Assert.assertNotNull(ssoTicketDto);
	}

	@Test
	public void testGetTicket() {
		String ticket = "";
		SSOTicketDto dto = ticketCache.getTicket(ticket);
        Assert.assertNull(dto);
	}
}
