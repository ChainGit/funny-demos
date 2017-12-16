package com.chain.project.unit.mapper;

import com.chain.project.base.mapper.BaseMapper;
import com.chain.project.common.domain.JsonMap;
import com.chain.project.unit.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface UserMapper extends BaseMapper<UserEntity, Long> {
    UserEntity findByName(String name);

    UserEntity findByUserId(Long userId);

    Map<String, Object> findLastComment(Long userId);

    int updateLastComment(JsonMap jsonMap);
}
