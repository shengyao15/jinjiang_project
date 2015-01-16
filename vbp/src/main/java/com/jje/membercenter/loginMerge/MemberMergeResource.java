package com.jje.membercenter.loginMerge;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.common.utils.MD5Utils;
import com.jje.common.utils.SHA1Utils;
import com.jje.dto.MemberBaseDto;
import com.jje.dto.membercenter.MergeLoginDto;
import com.jje.dto.membercenter.loginMerge.MemberMergeDto;
import com.jje.membercenter.domain.Member;

@Path("member/merge")
@Component
public class MemberMergeResource {
    private static final Logger LOG = LoggerFactory.getLogger(MemberMergeResource.class);


    @Autowired
    private MemberMergeService memberMergeService;
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/validate")
    public Response validate(MergeLoginDto mergeLoginDto) {
        try {
        	long begin = System.currentTimeMillis();
        	
        	String rawPwd = mergeLoginDto.getPassword();
        	String md5 = MD5Utils.generatePassword(rawPwd);
        	String sha1 = SHA1Utils.digestSha1JJ(rawPwd);
        	
            String userName = mergeLoginDto.getLoginName();
            MemberMergeDto memberMergeDto = new MemberMergeDto();
            
            // 查询v1表
            Member m1 = memberMergeService.queryMemberByLoginName1(userName);
            if (m1 != null) {
                boolean result = m1.validate(md5);
                if (result) {
                    memberMergeDto = m1.toMergeDto();
                    memberMergeDto.setCode("1");  
                    memberMergeDto.setRemark("登录成功");
                }else{
                	memberMergeDto.setCode("4");  
                	memberMergeDto.setRemark("密码错误");
                 }
                
                memberMergeDto.setDuration(System.currentTimeMillis() - begin);
                return Response.status(Status.OK).entity(memberMergeDto).build(); 
            }
            
        	// 查询v2表
            Member m2 = memberMergeService.queryMemberByLoginName2(userName);
        	if(m2 != null){
                boolean result = m2.validate(sha1);
                if (result) {
                    memberMergeDto = m2.toMergeDto();
                    memberMergeDto.setCode("1");  
                    memberMergeDto.setRemark("合并成功，登录成功");
                    
                    memberMergeService.updateVerifyStatus(userName,md5);
                }else{
                	memberMergeDto.setCode("4");  
                	memberMergeDto.setRemark("密码错误");
                }
                
                memberMergeDto.setDuration(System.currentTimeMillis() - begin);
                return Response.status(Status.OK).entity(memberMergeDto).build();
        	}
            
            // 查询v3表
        	List<Member> list = memberMergeService.queryMemberByLoginName3(userName);
        	
        	if(list != null && list.size()>0){
        		//先将状态置为 【密码错误】
        		memberMergeDto.setCode("4");
        		memberMergeDto.setRemark("密码错误");
        		
        		//如果有一条记录密码匹配  将状态置为 【需要验证信息】
        		for (Member m3 : list) {
            		boolean result = m3.validate(sha1);
    				if(result){
    					memberMergeDto.setCode("5");
    					memberMergeDto.setRemark("需要校验");
    					break;
    				}
    			}
        		
                memberMergeDto.setDuration(System.currentTimeMillis() - begin);
                return Response.status(Status.OK).entity(memberMergeDto).build();
        	}
        	
        	//在v1 v2 v3 表中都没有找到loginName  将状态置为 【用户名不存在】
        	memberMergeDto.setCode("3");
        	memberMergeDto.setRemark("用户名不存在");
            memberMergeDto.setDuration(System.currentTimeMillis() - begin);
            
            return Response.status(Status.OK).entity(memberMergeDto).build();
        } catch (Exception e) {
            LOG.error("登录发送错误：" + e.getMessage(), e);
            MemberMergeDto dto = new MemberMergeDto();
            dto.setRtcode(MemberBaseDto.ERROR_CODE);
            dto.setErrorMsg(e.getMessage());
            return Response.status(Status.OK).entity(dto).build();
        }
    }

    
    @POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	@Path("/prepareData1")
	public Response prepareData1(MergeLoginDto mergeLoginDto) {
		try {
			String loginName = mergeLoginDto.getLoginName();
			String password = mergeLoginDto.getPassword();
			
			memberMergeService.prepareData1(loginName, password);
        	
        	return Response.ok().entity(Status.OK).entity("OK").build();
			
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
    
    @POST
   	@Consumes(MediaType.APPLICATION_XML)
   	@Produces(MediaType.APPLICATION_XML)
   	@Path("/prepareData2")
   	public Response prepareData2(MergeLoginDto mergeLoginDto) {
   		try {
   			String loginName = mergeLoginDto.getLoginName();
   			String password = mergeLoginDto.getPassword();
   			
   			memberMergeService.prepareData2(loginName, password);
           	
           	return Response.ok().entity(Status.OK).entity("OK").build();
   			
   		} catch (Exception e) {
   			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
   		}
   	}
}