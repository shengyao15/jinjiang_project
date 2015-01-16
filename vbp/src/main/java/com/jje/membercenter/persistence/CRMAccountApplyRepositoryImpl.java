package com.jje.membercenter.persistence;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.Pagination;
import com.jje.dto.QueryMemberDto;
import com.jje.dto.ResultMemberDto;
import com.jje.dto.membercenter.CRMResponseDto;
import com.jje.dto.membercenter.accountapply.AccountMergeApplyDto;
import com.jje.dto.membercenter.accountapply.BuyCardApplyDto;
import com.jje.dto.membercenter.accountapply.ScoreRegisterApplyDto;
import com.jje.membercenter.crm.CRMAccountApplyProxy;
import com.jje.membercenter.domain.CRMAccountApplyRespository;
import com.jje.membercenter.xsd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class CRMAccountApplyRepositoryImpl implements CRMAccountApplyRespository
{

	@Autowired
	CRMAccountApplyProxy proxy;

	public CRMResponseDto applyRegisterScore(ScoreRegisterApplyDto dto) throws Exception
	{
		RegisterScoreRequest request = new RegisterScoreRequest();
		RegisterScoreResponse response = new RegisterScoreResponse();
		RegisterScoreRequest.Head head = new RegisterScoreRequest.Head();
		RegisterScoreRequest.Body body = new RegisterScoreRequest.Body();
		head.setTranscode(BigInteger.valueOf(30012));
		head.setSystype("JJ000");
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(dto.getStartDate()))));
		head.setSystype(dto.getCusumeType());
		body.setMembid(dto.getMemberId());
		StringBuilder str = new StringBuilder();
		str.append(dto.getCusumeType()).append("|");
		str.append(new SimpleDateFormat("yyyyMMddHHmmss").format(dto.getApplyDate())).append("|");
		if (dto.getCusumeType().equals("JJ003"))
		{
			str.append("锦江旅游").append("|");
			str.append(dto.getLineName()).append("|");
		}
		else if (dto.getCusumeType().equals("JJ004"))
		{
			str.append("锦江汽车").append("|");
			str.append(dto.getModel()).append("|");
		}
		else
		{
			str.append("锦江酒店").append("|");
			str.append(dto.getLodgeCity()).append("|");
			str.append(dto.getLodgeHotel()).append("|");
			str.append(dto.getRoomNo()).append("|");
		}
		str.append(new SimpleDateFormat("yyyyMMddHHmmss").format(dto.getStartDate())).append("|");
		str.append(new SimpleDateFormat("yyyyMMddHHmmss").format(dto.getEndDate())).append("|");
		str.append(dto.getComsumeAmount()).append("|");
		str.append(dto.getOrderNo()).append("|");
		str.append("补登积分：0").append("|");
		str.append(dto.getInvoceNo()).append("|");
		str.append(dto.getInvoicePicPath()).append("|");
		body.setSrdetail(str.toString());
		body.setSrtype("Credit Readd");
		request.setHead(head);
		request.setBody(body);
		response = proxy.applyRegisterScore(request);
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		crmResponseDto.setRetcode(response.getHead().getRetcode().toString());
		crmResponseDto.setRetmsg(response.getHead().getRetmsg());
		return crmResponseDto;
		}

	public ResultMemberDto<ScoreRegisterApplyDto> listRegisterScoreHistory(QueryMemberDto<ScoreRegisterApplyDto> queryDto) throws Exception
	{
		RegisterScoreHistoryRequest request = new RegisterScoreHistoryRequest();
		RegisterScoreHistoryResponse response = new RegisterScoreHistoryResponse();
		RegisterScoreHistoryRequest.Head head = new RegisterScoreHistoryRequest.Head();
		RegisterScoreHistoryRequest.Body body = new RegisterScoreHistoryRequest.Body();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setTranscode(BigInteger.valueOf(20012));
		head.setSystype("JJ000");
		body.setMembid(queryDto.getCondition().getMemberId());
		body.setSrtype("Credit Readd");
		request.setHead(head);
		request.setBody(body);
		response = proxy.listRegisterScoreHistory(request);
		ResultMemberDto<ScoreRegisterApplyDto> resultDto = new ResultMemberDto<ScoreRegisterApplyDto>();
		Pagination pagination = queryDto.getPagination();
		if (response.getBody().getListofsr() != null)
		{
			int records = response.getBody().getListofsr().getSr().size();
			int page = pagination.getPage();
			int rows = pagination.getRows();
			int total = records % rows == 0 ? records / rows : records / rows + 1;
			List<RegisterScoreHistoryResponse.Body.Listofsr.Sr> listDtos = new ArrayList<RegisterScoreHistoryResponse.Body.Listofsr.Sr>();
			if (response.getBody().getListofsr().getSr().size() > 0)
			{
				if (page < total)
				{
					for (int i = rows * (page - 1); i < rows * page; i++)
					{
						listDtos.add(response.getBody().getListofsr().getSr().get(i));
					}
				}
				else
				{
					for (int i = rows * (page - 1); i < response.getBody().getListofsr().getSr().size(); i++)
					{
						listDtos.add(response.getBody().getListofsr().getSr().get(i));
					}
				}
			}
			pagination.setRecords(records);
			pagination.countRecords(records);
			resultDto.setPagination(pagination);
			List<ScoreRegisterApplyDto> list = new ArrayList<ScoreRegisterApplyDto>();
			for (RegisterScoreHistoryResponse.Body.Listofsr.Sr s : listDtos)
			{
				ScoreRegisterApplyDto dto = new ScoreRegisterApplyDto();
				if (s.getSrdetail() != null)
				{
					String[] str = s.getSrdetail().split("\\|");
					dto.setApplyDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[1]));
					if (str[0].equals("JJ003"))
					{
						dto.setCusumeType(str[2]);
						dto.setLineName(str[3]);
						if (str[4] != null)
						{
							dto.setStartDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[4]));
						}
						if (str[5] != null)
						{
							dto.setEndDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[5]));
						}
						dto.setComsumeAmount(str[6]);
						dto.setOrderNo(str[7]);
						dto.setScore(Integer.parseInt(str[8].split("\\：")[1]));
						dto.setInvoceNo(str[8]);
						dto.setInvoicePicPath(str[9]);
					}
					else if (str[0].equals("JJ004"))
					{
						dto.setCusumeType(str[2]);
						dto.setModel(str[3]);
						if (str[4] != null)
						{
							dto.setStartDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[4]));
						}
						if (str[5] != null)
						{
							dto.setEndDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[5]));
						}
						dto.setComsumeAmount(str[6]);
						dto.setOrderNo(str[7]);
						dto.setScore(Integer.parseInt(str[8].split("\\：")[1]));
						dto.setInvoceNo(str[9]);
						dto.setInvoicePicPath(str[10]);
					}
					else
					{
						dto.setCusumeType(str[2]);
						dto.setLodgeCity(str[3]);
						dto.setLodgeHotel(str[4]);
						dto.setRoomNo(str[5]);
						if (str[4] != null)
						{
							dto.setStartDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[6]));
						}
						if (str[5] != null)
						{
							dto.setEndDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[7]));
						}
						dto.setComsumeAmount(str[8]);
						dto.setOrderNo(str[9]);
						dto.setScore(Integer.parseInt(str[10].split("\\：")[1]));
						dto.setInvoceNo(str[11]);
						dto.setInvoicePicPath(str[12]);
					}
				}
				// dto.setScore(null)
				dto.setStatus(s.getStatus());
				list.add(dto);
			}
			resultDto.setResults(list);
		}
		else
		{
			resultDto = null;
		}
		return resultDto;
	}

	public CRMResponseDto addAccountMergeApply(AccountMergeApplyDto dto) throws Exception
	{
		AccountMergeRequest request = new AccountMergeRequest();
		AccountMergeResponse response = new AccountMergeResponse();
		AccountMergeRequest.Head head = new AccountMergeRequest.Head();
		AccountMergeRequest.Body body = new AccountMergeRequest.Body();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setTranscode(BigInteger.valueOf(30012));
		head.setSystype("JJ000");
		body.setMembid(dto.getMemberId());
		body.setSrtype("Account Merge");
		StringBuilder str = new StringBuilder();
		str.append(dto.getMemberId()).append("|");
		if (dto.getAppylDate() != null)
		{
			str.append(new SimpleDateFormat("yyyyMMddHHmmss").format(dto.getAppylDate())).append("|");
		}
		else
		{
			str.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).append("|");
		}
		str.append(dto.getProvideMemberId()).append("|");
		str.append(dto.getTrueName()).append("|");
		str.append(dto.getPrimaryAddress()).append("|");
		str.append(dto.getEmail()).append("|");
		str.append(dto.getMobile()).append("|");
		if (dto.getBirthday() != null)
		{
			str.append(new SimpleDateFormat("yyyyMMddHHmmss").format(dto.getBirthday())).append("|");
		}
		else
		{
			str.append("").append("|");
		}
		body.setSrdetail(str.toString());
		request.setHead(head);
		request.setBody(body);
		response = proxy.addAccountMergeApply(request);
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		crmResponseDto.setRetcode(response.getHead().getRetcode().toString());
		crmResponseDto.setRetmsg(response.getHead().getRetmsg());
		return crmResponseDto;
		}

	public ResultMemberDto<AccountMergeApplyDto> listMergeApplyHistory(QueryMemberDto<AccountMergeApplyDto> queryDto) throws Exception
	{
		ListAccountMergeRequest request = new ListAccountMergeRequest();
		ListAccountMergeResponse response = new ListAccountMergeResponse();
		ListAccountMergeRequest.Head head = new ListAccountMergeRequest.Head();
		ListAccountMergeRequest.Body body = new ListAccountMergeRequest.Body();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setTranscode(BigInteger.valueOf(20012));
		head.setSystype("JJ000");
		body.setMembid(queryDto.getCondition().getMemberId());
		body.setSrtype("Account Merge");
		request.setHead(head);
		request.setBody(body);
		response = proxy.listMergeApplyHistory(request);
		ResultMemberDto<AccountMergeApplyDto> resultDto = new ResultMemberDto<AccountMergeApplyDto>();
		Pagination pagination = queryDto.getPagination();
		if (response.getBody().getListofsr() != null)
		{
			int records = response.getBody().getListofsr().getSr().size();
			int page = pagination.getPage();
			int rows = pagination.getRows();
			int total = records % rows == 0 ? records / rows : records / rows + 1;
			List<ListAccountMergeResponse.Body.Listofsr.Sr> listDtos = new ArrayList<ListAccountMergeResponse.Body.Listofsr.Sr>();
			if (response.getBody().getListofsr().getSr().size() > 0)
			{
				if (page < total)
				{
					for (int i = rows * (page - 1); i < rows * page; i++)
					{
						listDtos.add(response.getBody().getListofsr().getSr().get(i));
					}
				}
				else
				{
					for (int i = rows * (page - 1); i < response.getBody().getListofsr().getSr().size(); i++)
					{
						listDtos.add(response.getBody().getListofsr().getSr().get(i));
					}
				}
			}
			pagination.setRecords(records);
			pagination.countRecords(records);
			resultDto.setPagination(pagination);
			List<AccountMergeApplyDto> list = new ArrayList<AccountMergeApplyDto>();
			for (ListAccountMergeResponse.Body.Listofsr.Sr s : listDtos)
			{
				AccountMergeApplyDto dto = new AccountMergeApplyDto();
				if (s.getSrdetail() != null)
				{
					String[] str = s.getSrdetail().split("\\|");
					dto.setMemberId(str[0]);
					if (str[1] != null)
					{
						dto.setAppylDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[1]));
					}
					dto.setProvideMemberId(str[2]);
					dto.setTrueName(str[3]);
					dto.setPrimaryAddress(str[4]);
					dto.setEmail(str[5]);
					dto.setMobile(str[6]);
					if (str[7] != null)
					{
						dto.setBirthday(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[7]));
					}
				}
				dto.setStatus(s.getStatus());
				list.add(dto);
			}
			resultDto.setResults(list);
		}
		else
		{
			resultDto = null;
		}
		return resultDto;
	}

	public CRMResponseDto applyCard(BuyCardApplyDto dto) throws Exception
	{
		ApplyCardRequest request = new ApplyCardRequest();
		ApplyCardResponse response = new ApplyCardResponse();
		ApplyCardRequest.Head head = new ApplyCardRequest.Head();
		ApplyCardRequest.Body body = new ApplyCardRequest.Body();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setTranscode(BigInteger.valueOf(30012));
		head.setSystype("JJ000");
		body.setMembid(dto.getMemberId());
		body.setSrtype("Claims");
		StringBuilder str = new StringBuilder();
		str.append(dto.getApplyReasonType()).append("|");
		str.append(dto.getApplyReason()).append("|");
		str.append(new SimpleDateFormat("yyyyMMddHHmmss").format(dto.getApplyDate())).append("|");
		str.append(dto.getCardNo()).append("|");
		str.append(dto.getHierarchy()).append("|");
		str.append(dto.getMailAddress()).append("|");
		str.append(dto.getReceiverName()).append("|");
		str.append(dto.getContactTelephone()).append("|");
		body.setSrdetail(str.toString());
		request.setHead(head);
		request.setBody(body);
		response = proxy.applyCard(request);
		CRMResponseDto crmResponseDto = new CRMResponseDto();
		crmResponseDto.setRetcode(response.getHead().getRetcode().toString());
		crmResponseDto.setRetmsg(response.getHead().getRetmsg());
		return crmResponseDto;
		}

	public ResultMemberDto<BuyCardApplyDto> listApplyCard(QueryMemberDto<BuyCardApplyDto> queryDto) throws Exception
	{
		ListApplyCardRequest request = new ListApplyCardRequest();
		ListApplyCardResponse response = new ListApplyCardResponse();
		ListApplyCardRequest.Head head = new ListApplyCardRequest.Head();
		ListApplyCardRequest.Body body = new ListApplyCardRequest.Body();
		head.setReqtime(BigInteger.valueOf(Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))));
		head.setTranscode(BigInteger.valueOf(20012));
		head.setSystype("JJ000");
		body.setMembid(queryDto.getCondition().getMemberId());
		body.setSrtype("Claims");
		request.setHead(head);
		request.setHead(head);
		request.setBody(body);
		response = proxy.listApplyCard(request);
		ResultMemberDto<BuyCardApplyDto> resultDto = new ResultMemberDto<BuyCardApplyDto>();
		Pagination pagination = queryDto.getPagination();
		if (response.getBody().getListofsr() != null)
		{
			if (response.getBody().getListofsr().getSr() != null && !"".equals(response.getBody().getListofsr().getSr()))
			{
				int records = response.getBody().getListofsr().getSr().size();
				int page = pagination.getPage();
				int rows = pagination.getRows();
				int total = records % rows == 0 ? records / rows : records / rows + 1;
				List<ListApplyCardResponse.Body.Listofsr.Sr> listDtos = new ArrayList<ListApplyCardResponse.Body.Listofsr.Sr>();
				if (response.getBody().getListofsr().getSr().size() > 0)
				{
					if (page < total)
					{
						for (int i = rows * (page - 1); i < rows * page; i++)
						{
							listDtos.add(response.getBody().getListofsr().getSr().get(i));
						}
					}
					else
					{
						for (int i = rows * (page - 1); i < response.getBody().getListofsr().getSr().size(); i++)
						{
							listDtos.add(response.getBody().getListofsr().getSr().get(i));
						}
					}
				}
				pagination.setRecords(records);
				pagination.countRecords(records);
				resultDto.setPagination(pagination);
				List<BuyCardApplyDto> list = new ArrayList<BuyCardApplyDto>();
				for (ListApplyCardResponse.Body.Listofsr.Sr s : listDtos)
				{
					BuyCardApplyDto dto = new BuyCardApplyDto();
					if (s.getSrdetail() != null)
					{
						String[] str = s.getSrdetail().split("\\|");
						dto.setApplyReasonType(str[0]);
						dto.setApplyReason(str[1]);
						dto.setApplyDate(new SimpleDateFormat("yyyyMMddHHmmss").parse(str[2]));
						dto.setCardNo(str[3]);
						dto.setHierarchy(str[4]);
						dto.setMailAddress(str[5]);
						dto.setReceiverName(str[6]);
						dto.setContactTelephone(str[7]);
					}
					dto.setStatus(s.getStatus());
					list.add(dto);
				}
				resultDto.setResults(list);

			}
			else
			{
				resultDto = null;
			}
		}
		else
		{
			resultDto = null;
		}
		return resultDto;
	}
}
