package com.chain.project.unit.service;

import com.chain.project.base.service.BaseService;
import com.chain.project.common.domain.JsonMap;
import com.chain.project.unit.entities.UserEntity;

import java.util.Map;

public interface UserService extends BaseService<UserEntity, Long> {
    Map<String, Object> signIn(JsonMap jsonMap);

    boolean signUp(JsonMap jsonMap);

    Map<String, Object> briefInfo(JsonMap jsonMap);

    Map<String,Object> lastComment(JsonMap jsonMap);
}
