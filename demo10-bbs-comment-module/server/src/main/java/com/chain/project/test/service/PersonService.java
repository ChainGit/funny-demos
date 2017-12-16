package com.chain.project.test.service;


import com.chain.project.base.service.BaseService;
import com.github.pagehelper.PageInfo;
import com.chain.project.test.entities.PersonEntity;

import java.util.List;

public interface PersonService extends BaseService<PersonEntity, Long> {

    List<PersonEntity> queryListAll();

    PersonEntity findById(Long id);

    int update(PersonEntity personEntity);

    int deleteById(Long id);

    int insert(PersonEntity personEntity);

    PageInfo<PersonEntity> getPage(int current, int rows);
}
