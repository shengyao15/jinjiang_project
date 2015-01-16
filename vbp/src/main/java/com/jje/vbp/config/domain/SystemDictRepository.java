package com.jje.vbp.config.domain;


import java.util.List;

public interface SystemDictRepository {


    List<SystemDict> queryAll();
    Long getIdByKey(String key);
    String  getByKey(String key);
    List<String> getByType(String type);
    void update(SystemDict systemDict);
    void save(SystemDict systemDict);
    void deleteByKey(String key);
    void deleteByType(String key);
    
    void deleteById(Long id);

}
