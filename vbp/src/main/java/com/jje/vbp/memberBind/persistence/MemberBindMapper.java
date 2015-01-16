package com.jje.vbp.memberBind.persistence;


import com.jje.dto.membercenter.memberbind.MemberInfoResultDto;
import com.jje.vbp.memberBind.domain.MemberBindEntity;

public interface MemberBindMapper {
	
    void bindMember(MemberBindEntity memberBindEntity);

    int queryBindMemberCount(MemberBindEntity memberBindEntity);
    
    MemberInfoResultDto queryMemberInfo(String memberID);
    
    String getMemberIdByKey(MemberBindEntity memberBindEntity);
    
    String getMcMemberCodeByKey(MemberBindEntity memberBindEntity);
}
