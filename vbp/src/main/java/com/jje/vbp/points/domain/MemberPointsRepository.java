package com.jje.vbp.points.domain;


import com.jje.vbp.points.persistence.MemberPointsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;

@Repository
public class MemberPointsRepository {

    @Autowired
    private MemberPointsMapper mapper;

    public MemberPoints  queryPointsAtPrevMonth(String memNum){
        MemberPoints memberPoints = mapper.queryPointsAtPrevMonth(memNum);
            return memberPoints;

    }






}
