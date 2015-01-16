package com.jje.membercenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jje.common.utils.RestClient;
import com.jje.dto.membercenter.AccountMergeDto;
import com.jje.membercenter.domain.MergeMcMemberCodeLog;

@Component
public class MergeOrderSerive {
	@Autowired
	private RestClient restClient;

	@Value(value = "${hbp.url}")
	private String hbpUrl;

	@Value(value = "${tbp.url}")
	private String tbpUrl;

	@Value(value = "${abp.url}")
	private String abpUrl;

	@Value(value = "${fbp.url}")
	private String fbpUrl;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

	public void mergeOrderMcMemberCode(AccountMergeDto dto) throws Exception {
		MergeMcMemberCodeLog log = new MergeMcMemberCodeLog(dto);
		boolean mergeSuccessful = true;
		String mergedModule = "";

		if (mergeMcMemberCodeForHotelOrder(dto)) {
			mergedModule += "HOTEL,";
		} else {
			mergeSuccessful = false;
		}

		if (mergeMcMemberCodeForAutoOrder(dto)) {
			mergedModule += "AUTO,";
		} else {
			mergeSuccessful = false;
		}

		if (mergeMcMemberCodeForTravelOrder(dto)) {
			mergedModule += "TRAVEL,";
		} else {
			mergeSuccessful = false;
		}

		if (mergeMcMemberCodeForFlightOrder(dto)) {
			mergedModule += "FLIGHT,";
		} else {
			mergeSuccessful = false;
		}

		log.setUpdatedModule(mergedModule);

		if (!mergeSuccessful) {
			logger.error(log.toString());
			throw new RuntimeException("Merge Mc member code");
		}
	}

	public boolean mergeMcMemberCodeForHotelOrder(AccountMergeDto dto) {
		return mergeMcMemberForDifferentModule(dto, hbpUrl+ "/hotels/order/mergeOrderMcMemberCode");
	}

	public boolean mergeMcMemberCodeForFlightOrder(AccountMergeDto dto) {
		return mergeMcMemberForDifferentModule(dto, fbpUrl+ "/ticket/order/mergeOrderMcMemberCode");
	}

	public boolean mergeMcMemberCodeForAutoOrder(AccountMergeDto dto) {
		return mergeMcMemberForDifferentModule(dto, abpUrl+ "/autorental/order/mergeOrderMcMemberCode");
	}

	public boolean mergeMcMemberCodeForTravelOrder(AccountMergeDto dto) {
		return mergeMcMemberForDifferentModule(dto, tbpUrl+ "/travel/order/mergeOrderMcMemberCode");
	}

	private boolean mergeMcMemberForDifferentModule(AccountMergeDto dto,
			String url) {
		try {
			restClient.post(url, dto, String.class);
		} catch (Exception e) {
			logger.error("mergeMcMemberForDifferentModule  error, mergeInfo:["+dto.getMainAccountMcMemberCode()+"", e);
			return false;
		}
		return true;
	}
}
