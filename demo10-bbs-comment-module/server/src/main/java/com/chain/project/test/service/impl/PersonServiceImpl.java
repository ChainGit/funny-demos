package com.chain.project.test.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.chain.project.base.service.impl.AbstractService;
import com.chain.project.common.utils.ChainProjectUtils;
import com.chain.project.test.entities.PersonEntity;
import com.chain.project.test.mapper.PersonMapper;
import com.chain.project.test.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("personService")
public class PersonServiceImpl extends AbstractService<PersonEntity, Long> implements PersonService {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<PersonEntity> queryListAll() {
        return personMapper.queryListAll();
    }

    @Override
    public PersonEntity findById(Long id) {
        return personMapper.findById(id);
    }

    @Transactional
    @Override
    public int update(PersonEntity personEntity) {
        int num = personMapper.update(personEntity);
        ChainProjectUtils.randomDisaster();
        return num;
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        int num = personMapper.deleteById(id);
        ChainProjectUtils.randomDisaster();
        return num;
    }

    @Transactional
    @Override
    public int insert(PersonEntity personEntity) {
        int num = personMapper.insert(personEntity);
        ChainProjectUtils.randomDisaster();
        return num;
    }

    @Override
    public PageInfo<PersonEntity> getPage(int currentPageNum, int eachPageRows) {
        PageHelper.startPage(currentPageNum, eachPageRows);
        List<PersonEntity> list = this.queryListAll();
        PageInfo<PersonEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

}
