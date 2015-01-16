/**
 *
 */
package com.jje.membercenter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.MemberCardOrderDto;
import com.jje.dto.membercenter.MemberOrderNoteDto;
import com.jje.membercenter.domain.MemberCardOrder;
import com.jje.membercenter.domain.MemberCardOrderAdminRepository;
import com.jje.membercenter.domain.MemberCardOrderRepository;
import com.jje.payment.dto.RefundNoticeDto;

/**
 * @author Z_Xiong
 */
@Path("member")
@Component
public class MemberAdminResource {

    private static final Logger LOG = LoggerFactory.getLogger(MemberResource.class);

    @Autowired
    private MemberCardOrderAdminRepository MemberCardOrderAdminRepository;

    @Autowired
    MemberCardOrderRepository memberCardOrderRepositoryImpl;


    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/adminOrder")
    public Response queryMemberCardOrderList(QueryMemberDto<MemberCardOrderDto> memberCardOrderDto) {
        try {
            if(LOG.isDebugEnabled()) LOG.debug("------------start MemberAdminResource.queryMemberCardOrderList(cardNo {})", memberCardOrderDto.getCondition().getCardNo());
            ResultMemberDto<MemberCardOrderDto> resultDtos = MemberCardOrderAdminRepository.queryAdminMemberCardOrder(memberCardOrderDto);
            LOG.warn("------MemberAdminResource.queryMemberCardOrderList(cardNo {})----resultSize={}",memberCardOrderDto.getCondition().getCardNo(),resultDtos.getResults().size());
            return Response.ok().entity(resultDtos).build();
        } catch (Exception e) {
            LOG.error("------------MemberAdminResource.queryMemberCardOrderList(cardNo {}) error!!------", memberCardOrderDto.getCondition().getCardNo(), e);
            return Response.ok().entity("failure").build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/orderNote")
    public Response getMemberOrderNoteByOrderNo(String orderNo) {
        try {
            if(LOG.isDebugEnabled()) LOG.debug("------------start MemberAdminResource.getMemberOrderNoteByOrderNo({})-----", orderNo);
            ResultMemberDto<MemberOrderNoteDto> resultDtos = MemberCardOrderAdminRepository.getMemberOrderNoteByOrderNo(orderNo);
            return Response.ok().entity(resultDtos).build();
        } catch (Exception e) {
            LOG.error("------------MemberAdminResource.getMemberOrderNoteByOrderNo({}) error!!----- ", orderNo,e);
            return Response.ok().entity("failure").build();
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/refund/notify")
    public Response refundNotify(RefundNoticeDto noticeDto) {
        LOG.debug("start 执行更新卡订单订单为退款状态操作订单号:{}", noticeDto.getOrderNo());
        if (noticeDto == null || noticeDto.getOrderNo() == null || noticeDto.getRefundAmount() == null) {
            LOG.warn("-----MemberAdminResource.refundNotify({}) 更新订单状态发生错误，传入退款信息不存在",noticeDto);
            return Response.ok().entity("failure").build();
        }
        if (LOG.isDebugEnabled()) {
            LOG.warn("-----MemberAdminResource.refundNotify(orderNo {}) 会员卡订单版块退款后更新订单状态，传入订单号为：" + noticeDto.getOrderNo(),noticeDto.getOrderNo());
        }

        try {
            MemberCardOrder cardOrder = new MemberCardOrder();
            cardOrder.setRefundAmount(noticeDto.getRefundAmount());
            cardOrder.setOrderNo(noticeDto.getOrderNo());
            cardOrder.setPayStatus(4);
            memberCardOrderRepositoryImpl.updateStatus(cardOrder);
            return Response.ok().entity("success").build();
        } catch (Exception ex) {
            LOG.error("-----MemberAdminResource.refundNotify(orderNo {}) 更新订单状态出错;订单号：" + noticeDto.getOrderNo() + ";错误信息：" + ex.getMessage(),noticeDto.getOrderNo(), ex);
            return Response.ok().entity("failure").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/updateCardOrder")
    public Response updateCardOrder(MemberCardOrderDto cardDto) {
        try {
            MemberCardOrder cardOrder = new MemberCardOrder(cardDto);
            memberCardOrderRepositoryImpl.updateStatus(cardOrder);
            return Response.ok().entity("ok").build();
        } catch (Exception e) {
            LOG.error("-----MemberAdminResource.updateCardOrder(cardNo {})  error!! " + e.getMessage(),cardDto.getCardNo(), e);
            return Response.ok().entity("failure").build();
        }

    }


    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/saveOrderNote")
    public Response saveMemberOrderNote(MemberOrderNoteDto noteDto) {
        try {
            MemberCardOrderAdminRepository.saveMemberOrderNote(noteDto);
            return Response.ok().entity("ok").build();
        } catch (Exception e) {
            LOG.error("--------MemberAdminResource.saveMemberOrderNote(userName {})  error!! " + e.getMessage(),noteDto.getUserName(), e);
            return Response.ok().entity("failure").build();
        }

    }
}
