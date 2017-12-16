package com.chain.project.unit.service.impl;

import com.chain.project.base.service.impl.AbstractService;
import com.chain.project.common.directory.Constant;
import com.chain.project.common.domain.JsonMap;
import com.chain.project.common.utils.ChainProjectUtils;
import com.chain.project.unit.domain.CommentReplyTree;
import com.chain.project.unit.entities.CommentEntity;
import com.chain.project.unit.mapper.CommentMapper;
import com.chain.project.unit.mapper.UserMapper;
import com.chain.project.unit.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("commentService")
public class CommentServiceImpl extends AbstractService<CommentEntity, Long> implements CommentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean addMain(JsonMap jsonMap) {
        Long userId = jsonMap.getParsedLong("userId");
        String text = jsonMap.getParsedString("text");
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUserId(userId);
        commentEntity.setText(text);
        int insert = commentMapper.insert(commentEntity);
        if (!ChainProjectUtils.isPositive(insert))
            return false;
        Long id = commentEntity.getId();
        commentEntity.setCommentId(id);
        int update = commentMapper.update(commentEntity);
        if (!ChainProjectUtils.isPositive(update))
            return false;
        Date date = new Date();
        jsonMap.put("lastComment", date);
        update = userMapper.updateLastComment(jsonMap);
        if (ChainProjectUtils.isPositive(update))
            return true;
        return false;
    }

    @Override
    public Map<String, Object> findMain(JsonMap jsonMap) {
        Integer rows = jsonMap.getParsedInteger("rows");
        Integer page = jsonMap.getParsedInteger("page");
        PageHelper.startPage(page, rows);
        List<Map<String, Object>> list = commentMapper.findMain();
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put(Constant.EACH_PAGE_RECORDS, pageInfo.getList());
        resMap.put(Constant.TOTAL_PAGES, pageInfo.getPages());
        resMap.put(Constant.TOTAL_RECORDS, pageInfo.getTotal());
        return resMap;
    }

    @Override
    public boolean deleteMain(JsonMap jsonMap) {
        return deleteReply(jsonMap);
    }

    @Override
    public boolean addReply(JsonMap jsonMap) {
        Long userId = jsonMap.getParsedLong("userId");
        Long parentCommentId = jsonMap.getParsedLong("parentCommentId");
        String text = jsonMap.getParsedString("text");
        CommentEntity commentEntity = commentMapper.findByCommentId(parentCommentId);
        if (ChainProjectUtils.isEmpty(commentEntity))
            return false;
        commentEntity = new CommentEntity();
        commentEntity.setUserId(userId);
        commentEntity.setText(text);
        commentEntity.setParentId(parentCommentId);
        int insert = commentMapper.insert(commentEntity);
        if (!ChainProjectUtils.isPositive(insert))
            return false;
        Long id = commentEntity.getId();
        commentEntity.setCommentId(id);
        int update = commentMapper.update(commentEntity);
        if (ChainProjectUtils.isPositive(update))
            return true;
        return false;
    }

    @Override
    public boolean deleteReply(JsonMap jsonMap) {
        Long commentId = jsonMap.getParsedLong("commentId");
        if (ChainProjectUtils.isEmpty(commentId))
            return false;
        int delete = commentMapper.deleteReply(commentId);
        if (ChainProjectUtils.isPositive(delete))
            return true;
        return false;
    }

    @Override
    public Object findReply(JsonMap jsonMap) throws IOException {
        Long parentCommentId = jsonMap.getParsedLong("parentCommentId");
        if (ChainProjectUtils.isEmpty(parentCommentId))
            return null;
        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> list = commentMapper.findReply(parentCommentId);
        if (ChainProjectUtils.isEmpty(list))
            return null;
        Object json = CommentReplyTree.make(list);
        if (!ChainProjectUtils.isEmpty(json))
            return json;
        return null;
    }

}
