package com.jje.vbp.config.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jje.vbp.config.domain.SystemDict;
import com.jje.vbp.config.domain.SystemDictRepository;

@Component
public class SystemDictRepositoryImpl implements SystemDictRepository {

    @Autowired
    private SystemDictMapper mapper;

    public List<SystemDict> queryAll() {
        return mapper.queryAll();
    }

    public String getByKey(String key) {
        return mapper.getByKey(key);
    }

    public List<String> getByType(String type) {
        return mapper.getByType(type);
    }

    public void update(SystemDict systemDict) {
        mapper.update(systemDict);
    }

    public void save(SystemDict systemDict) {
        mapper.insert(systemDict);
    }

    public void deleteByKey(String key) {
        mapper.deleteByKey(key);
    }

    public void deleteByType(String type) {
       mapper.deleteByType(type);
    }

    public void deleteById(Long id) {
      mapper.deleteById(id);
    }

	public Long getIdByKey(String key) {
		return mapper.getIdByKey(key);
	}

}
