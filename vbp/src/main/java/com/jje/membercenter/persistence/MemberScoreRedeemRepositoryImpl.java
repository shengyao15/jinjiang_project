package com.jje.membercenter.persistence;

import com.jje.membercenter.domain.MemberScoreRedeem;
import com.jje.membercenter.domain.MemberScoreRedeemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MemberScoreRedeemRepositoryImpl implements MemberScoreRedeemRepository
{

    @Autowired
    MemberScoreRedeemMapper memberScoreRedeemMapper;

    public void addScoreRedeem(MemberScoreRedeem memberScoreRedeem) {
        memberScoreRedeemMapper.addScoreRedeem(memberScoreRedeem);
    }

    public List<MemberScoreRedeem> queryRedeem(MemberScoreRedeem memberScoreRedeem) {
        return memberScoreRedeemMapper.queryRedeem(memberScoreRedeem);
    }
}
