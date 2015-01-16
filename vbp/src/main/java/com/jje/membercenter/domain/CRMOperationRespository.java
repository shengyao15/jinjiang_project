package com.jje.membercenter.domain;

import com.jje.dto.member.score.MemberScoreDto;
import com.jje.dto.member.score.QueryScoreDto;
import com.jje.dto.member.taobao.TaobaoPointsRedeemInDto;
import com.jje.dto.member.taobao.TaobaoPointsRedeemOutDto;
import com.jje.dto.nbp.TemplateDto;
import com.jje.membercenter.xsd.TaobaoQueryScoreResponse;
import com.jje.membercenter.xsd.TaobaoRedeemResponse;

public interface CRMOperationRespository
{
	MemberScoreDto queryScore(QueryScoreDto queryScoreDto) throws Exception;
	
	boolean syncTemplateToCrm(TemplateDto templateDto) throws Exception;
	
	String queryChannelScore(String orderNum);

	TaobaoQueryScoreResponse queryTaobaoScore(String membid);

	TaobaoRedeemResponse redeem(TaobaoPointsRedeemInDto request, String source);
	
}
