package com.jje.vbp.config.domain;


import com.jje.common.utils.StringUtils;
import com.jje.dto.member.MemberCardLvChanelRelDto;
import com.jje.dto.vbp.config.SystemDictDto;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class SystemDict {

    private Long id;

    public String key ;

    private String type;

    private String value;

    public SystemDict() {
    }

    public SystemDict(String level,List<String> channels) {
        super();
        this.key = level;
        this.value = splitList(channels);

    }

    public SystemDict(SystemDictDto dto){
       super();
        this.id = dto.getId();
        this.key = dto.getKey();
        this.type = dto.getType();
        this.value = dto.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String splitList(List<String> list){
       return  StringUtils.join(list,",");
    }


    public SystemDictDto toDto(){
        SystemDictDto dto = new SystemDictDto();
        BeanUtils.copyProperties(this, dto);
        return dto;
    }


}
