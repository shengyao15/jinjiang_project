package com.jje.vbp.memberCardLvChanelRel;

import com.jje.common.utils.ResourceInvokeHandler;
import com.jje.dto.member.MemberCardLvChanelRelDto;
import com.jje.dto.member.MemberCardLvChanelRelDtos;
import com.jje.membercenter.DataPrepareFramework;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class MemberCardLvChanelRelTest  extends DataPrepareFramework {

    @Autowired
    private ResourceInvokeHandler resourceInvokeHandler;

    @Test
    public void test_save_member_card_lv_chanel_rel_dtos_success(){
        ResourceInvokeHandler.InvokeResult result = 
                resourceInvokeHandler.doPost("memberCardLvChanelRelResource",MemberCardLvChanelRelResource.class,"/memberCardLvChanelRel/saveAndUpdate",getData().getMemberCardLvChanelRelDto().get(0), null);
        ResourceInvokeHandler.InvokeResult result3 =
                resourceInvokeHandler.doPost("memberCardLvChanelRelResource",MemberCardLvChanelRelResource.class,"/memberCardLvChanelRel/saveAndUpdate",getData2().getMemberCardLvChanelRelDto().get(0), null);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        ResourceInvokeHandler.InvokeResult<MemberCardLvChanelRelDto> result2 =
                resourceInvokeHandler.doGet("memberCardLvChanelRelResource",MemberCardLvChanelRelResource.class,"/memberCardLvChanelRel/queryChannelByKey/锦卡222",MemberCardLvChanelRelDto.class);
        Assert.assertEquals(Response.Status.OK, result2.getStatus());
        Assert.assertEquals(result2.getOutput().getCardLevel(),"锦卡222");
        Assert.assertEquals(result2.getOutput().getChannel().get(0),"养猪场2");
    }

    @Test
    public void test_query_success_return_dto_size_3(){
    	 ResourceInvokeHandler.InvokeResult result1 = 
                 resourceInvokeHandler.doPost("memberCardLvChanelRelResource",MemberCardLvChanelRelResource.class,"/memberCardLvChanelRel/saveAndUpdate",getData3(), null);
        ResourceInvokeHandler.InvokeResult<MemberCardLvChanelRelDto> result =
        resourceInvokeHandler.doGet("memberCardLvChanelRelResource",MemberCardLvChanelRelResource.class,"/memberCardLvChanelRel/queryChannelByKey/锦卡231321",MemberCardLvChanelRelDto.class);
        Assert.assertEquals(Response.Status.OK, result.getStatus());
        Assert.assertEquals(result.getOutput().getChannel().size(),3);
        Assert.assertEquals(result.getOutput().getChannel().get(0),"养猪场");
    }



    @Test
    public void spilt(){
        List<String> list =new ArrayList<String>();
        list.add("321");
        list.add("321");
        list.add("321");
        list.add("321");
        StringBuffer sb = new StringBuffer();
        for (String s : list) {
            sb.append(s+",");
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb.toString());


    }
    
    public MemberCardLvChanelRelDto getData3() {
        MemberCardLvChanelRelDtos mcDto = new MemberCardLvChanelRelDtos();
        List<MemberCardLvChanelRelDto> relDtos=mcDto.getMemberCardLvChanelRelDto();
        List<String> channels = new ArrayList<String>();
        channels.add("养猪场");
        channels.add("西瓜田");
        channels.add("锦卡");
        MemberCardLvChanelRelDto m1 = new MemberCardLvChanelRelDto();
        m1.setCardLevel("锦卡231321");
        m1.setChannel(channels);
        relDtos.add(m1);
        mcDto.setMemberCardLvChanelRelDto(relDtos);
        return relDtos.get(0);
    }



    public MemberCardLvChanelRelDtos getData(){
        MemberCardLvChanelRelDtos mcDto = new MemberCardLvChanelRelDtos();
        List<MemberCardLvChanelRelDto> relDtos=mcDto.getMemberCardLvChanelRelDto();
        List<String> channels = new ArrayList<String>();
        channels.add("养猪场");
        channels.add("西瓜田");
        MemberCardLvChanelRelDto m1 = new MemberCardLvChanelRelDto();
        m1.setCardLevel("锦卡222");
        m1.setChannel(channels);
        relDtos.add(m1);
        mcDto.setMemberCardLvChanelRelDto(relDtos);
        return mcDto;
    }

    public MemberCardLvChanelRelDtos getData2(){
        MemberCardLvChanelRelDtos mcDto = new MemberCardLvChanelRelDtos();
        List<MemberCardLvChanelRelDto> relDtos=mcDto.getMemberCardLvChanelRelDto();
        List<String> channels = new ArrayList<String>();
        channels.add("养猪场2");
        channels.add("西瓜田");
        MemberCardLvChanelRelDto m1 = new MemberCardLvChanelRelDto();
        m1.setCardLevel("锦卡222");
        m1.setChannel(channels);
        relDtos.add(m1);
        mcDto.setMemberCardLvChanelRelDto(relDtos);
        return mcDto;
    }
}
