package com.jje.vbp.sns.persistence;

import java.util.List;

import com.jje.vbp.sns.domain.ThirdpartyBind;

public interface ThirdpartyBindMapper {

	void save(ThirdpartyBind thirdpartyBind);

	List<ThirdpartyBind> query(ThirdpartyBind thirdpartyBind);
	
	List<ThirdpartyBind> queryByMcMemberCode(ThirdpartyBind thirdpartyBind);
	
	List<ThirdpartyBind> queryBySign(ThirdpartyBind thirdpartyBind);

}
