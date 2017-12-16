package com.chain.project.unit.service;

import com.chain.project.base.service.BaseService;
import com.chain.project.common.domain.JsonMap;
import com.chain.project.unit.entities.CommentEntity;

import java.io.IOException;
import java.util.Map;

public interface CommentService extends BaseService<CommentEntity, Long> {
    boolean addMain(JsonMap jsonMap);

    Map<String, Object> findMain(JsonMap jsonMap);

    boolean deleteMain(JsonMap jsonMap);

    boolean addReply(JsonMap jsonMap);

    boolean deleteReply(JsonMap jsonMap);

    Object findReply(JsonMap jsonMap) throws IOException;
}
