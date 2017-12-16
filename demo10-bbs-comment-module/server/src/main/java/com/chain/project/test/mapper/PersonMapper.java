package com.chain.project.test.mapper;

import com.chain.project.base.mapper.BaseMapper;
import com.chain.project.test.entities.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.List;

//java可以也仅仅可以多继承（或者实现）接口，类是单继承
@Component
public interface PersonMapper extends BaseMapper<PersonEntity, Long> {

    int insert(PersonEntity personEntity);

    int update(PersonEntity personEntity);

    int deleteById(Long id);

    PersonEntity findById(Long id);

    List<PersonEntity> queryListAll();
}
