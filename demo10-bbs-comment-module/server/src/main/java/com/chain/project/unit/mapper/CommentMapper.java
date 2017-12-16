package com.chain.project.unit.mapper;

import com.chain.project.base.mapper.BaseMapper;
import com.chain.project.unit.entities.CommentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface CommentMapper extends BaseMapper<CommentEntity, Long> {

    List<Map<String, Object>> findMain();

    CommentEntity findByCommentId(Long parentCommentId);

    int deleteReply(Long commentId);

    List<Map<String, Object>> findReply(Long parentCommentId);
}
