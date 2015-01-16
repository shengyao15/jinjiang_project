package com.jje.vbp.memberCardLvChanelRel.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.member.MemberCardLvChanelRelDto;
import com.jje.vbp.config.domain.SystemDict;
import com.jje.vbp.config.domain.SystemDictRepository;

@Service
public class MemberCardLvChanelRelService {

	private Logger logger = LoggerFactory.getLogger(MemberCardLvChanelRelService.class);
	
    @Autowired
    private SystemDictRepository repository;

    public List<String> queryChannelByKey(String key){
        List<String> stringList = new ArrayList<String>();
        try{
        String channel = repository.getByKey(key);
        if(StringUtils.isNotBlank(channel)){
             String[]  arrChannel = channel.split(",");
             stringList = Arrays.asList(arrChannel);
        }
        }catch (Exception e) {
        	logger.error(e.getMessage());
		}
        return stringList;
    }

    public void saveAndUpdate(MemberCardLvChanelRelDto memberCardLvChanelRelDto) {
        if(null!=memberCardLvChanelRelDto){
                String level = memberCardLvChanelRelDto.getCardLevel();
                String systemDict = repository.getByKey(level);
                if(StringUtils.isBlank(systemDict)){
                    SystemDict  dict= new SystemDict(memberCardLvChanelRelDto.getCardLevel(),memberCardLvChanelRelDto.getChannel());
                    repository.save(dict);
                }else{
                	Long id = repository.getIdByKey(level);
                    SystemDict  dict= new SystemDict(memberCardLvChanelRelDto.getCardLevel(),memberCardLvChanelRelDto.getChannel());
                    dict.setId(id);
                    repository.update(dict);
                }
        }
    }


}
