package com.jje.vbp.config.persistence;


import com.jje.vbp.config.domain.SystemDict;

import java.util.List;

public interface  SystemDictMapper {

      List<SystemDict> queryAll();
      Long getIdByKey(String key);
      String  getByKey(String key);
      List<String> getByType(String type);
      void update(SystemDict systemDict);
      void insert(SystemDict systemDict);
      void deleteByKey(String key);
      void deleteByType(String type);
      void deleteById(Long id);


}
