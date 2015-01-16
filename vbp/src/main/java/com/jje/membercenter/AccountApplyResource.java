package com.jje.membercenter;

import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.accountapply.AccountMergeApplyDto;
import com.jje.dto.membercenter.accountapply.BuyCardApplyDto;
import com.jje.dto.membercenter.accountapply.ScoreRegisterApplyDto;
import com.jje.membercenter.domain.CRMAccountApplyRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("accountApply")
@Component
public class AccountApplyResource {
    private static final Logger LOG = LoggerFactory.getLogger(AccountApplyResource.class);
    @Autowired
    CRMAccountApplyRespository crmAccountApplyRepository;

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/applyRegisterScore")
    public Response applyRegisterScore(ScoreRegisterApplyDto scoreRegisterApplyDto) throws Exception {
        try {
            CRMResponseDto dto = crmAccountApplyRepository.applyRegisterScore(scoreRegisterApplyDto);
            if (LOG.isWarnEnabled())
                LOG.warn("-----return:{} AccountApplyResource.applyRegisterScore(membid {})----membrowid: " + dto.getMembid() + ", retcode: " + dto.getRetcode() + ", retmsg: " + dto.getRetmsg(), dto, scoreRegisterApplyDto.getMemberId());
            return Response.ok(dto).build();
        } catch (Exception e) {
            LOG.error("-----AccountApplyResource.applyRegisterScore(membid {})----error---- ", scoreRegisterApplyDto.getMemberId(), e);
            return Response.status(Status.NOT_MODIFIED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listRegisterScoreHistory")
    public Response listRegisterScoreHistory(QueryMemberDto<ScoreRegisterApplyDto> queryDto) throws Exception {
        ResultMemberDto<ScoreRegisterApplyDto> resultDto = crmAccountApplyRepository.listRegisterScoreHistory(queryDto);
        if (resultDto == null) {
            LOG.warn("-------AccountApplyResource.listRegisterScoreHistory() resultDto is null!!-------");
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(resultDto).build();
        // Response.ok(accountApplyRepository.listRegisterScoreHistory(queryDto)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/addAccountMergeApply")
    public Response addAccountMergeApply(AccountMergeApplyDto accountMergeApplyDto) throws Exception {
        try {
            CRMResponseDto dto = crmAccountApplyRepository.addAccountMergeApply(accountMergeApplyDto);
            if (LOG.isWarnEnabled())
                LOG.warn("-----return:{} AccountApplyResource.applyRegisterScore(membid {})----membrowid: " + dto.getMembid() + ", retcode: " + dto.getRetcode() + ", retmsg: " + dto.getRetmsg(), dto, accountMergeApplyDto.getMemberId());
            return Response.ok(dto).build();
        } catch (Exception e) {
            LOG.error("------AccountApplyResource.applyRegisterScore(mobile {}) error!!------", accountMergeApplyDto.getMobile(), e);
            return Response.status(Status.NOT_MODIFIED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listMergeApplyHistory")
    public Response listMergeApplyHistory(QueryMemberDto<AccountMergeApplyDto> queryDto) {
        try {
            ResultMemberDto<AccountMergeApplyDto> resultDto = crmAccountApplyRepository.listMergeApplyHistory(queryDto);
            if (resultDto == null) {
                if (LOG.isWarnEnabled())
                    LOG.warn("------AccountApplyResource.listMergeApplyHistory() resultDto is null----");
                return Response.status(Status.NOT_FOUND).build();
            }
            return Response.ok().entity(resultDto).build();
        } catch (Exception e) {
            LOG.error("------AccountApplyResource.listMergeApplyHistory(mobile {})------error ", queryDto.getCondition().getMobile(), e);
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/applyCard")
    public Response applyCard(BuyCardApplyDto buyCardApplyDto) throws Exception {
        try {
            CRMResponseDto dto = crmAccountApplyRepository.applyCard(buyCardApplyDto);
            if (LOG.isWarnEnabled())
                LOG.warn("-----return:{} AccountApplyResource.applyCard(membid {})----resultDto is null----: " + dto.getMembid() + ", retcode: " + dto.getRetcode() + ", retmsg: " + dto.getRetmsg(), dto, buyCardApplyDto.getMemberId());
            return Response.ok(dto).build();
        } catch (Exception e) {
            LOG.error("------AccountApplyResource.applyCard(membid {}) error ", buyCardApplyDto.getMemberId(), e);
            return Response.status(Status.NOT_MODIFIED).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("/listApplyCard")
    public Response listApplyCard(QueryMemberDto<BuyCardApplyDto> queryDto) throws Exception {
        ResultMemberDto<BuyCardApplyDto> resultDto = crmAccountApplyRepository.listApplyCard(queryDto);
        if (resultDto == null) {
            if (LOG.isWarnEnabled())
                LOG.warn("-----AccountApplyResource.listApplyCard(membid {})----", queryDto.getCondition().getMemberId());
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().entity(resultDto).build();
    }
}
