package com.jje.vbp.points.persistence;


import com.jje.vbp.points.domain.MemberPoints;

import java.util.Date;

public interface MemberPointsMapper {
    public MemberPoints queryPointsAtPrevMonth(String memNum);

}
