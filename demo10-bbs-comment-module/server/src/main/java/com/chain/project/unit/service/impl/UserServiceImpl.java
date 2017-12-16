package com.chain.project.unit.service.impl;

import com.chain.project.base.service.impl.AbstractService;
import com.chain.project.common.domain.JsonMap;
import com.chain.project.common.utils.ChainProjectUtils;
import com.chain.project.unit.entities.UserEntity;
import com.chain.project.unit.mapper.UserMapper;
import com.chain.project.unit.service.UserService;
import com.chain.project.unit.utils.BbsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends AbstractService<UserEntity, Long> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> signIn(JsonMap jsonMap) {
        String username = jsonMap.getParsedString("name");
        String password = jsonMap.getParsedString("password");
        UserEntity userEntity = userMapper.findByName(username);
        //判断用户是否存在
        if (ChainProjectUtils.isEmpty(userEntity))
            return null;
        boolean verifyResult = BbsUtils.verifyPassword(userEntity, password);
        if (verifyResult) {
            Long userId = userEntity.getUserId();
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("userId", userId);
            return resMap;
        }
        return null;
    }

    @Override
    public boolean signUp(JsonMap jsonMap) {
        String username = jsonMap.getParsedString("name");
        String password = jsonMap.getParsedString("password");
        UserEntity userEntity = userMapper.findByName(username);
        //判断用户是否存在
        if (!ChainProjectUtils.isEmpty(userEntity))
            return false;
        userEntity = new UserEntity();
        userEntity.setName(username);
        String avatarIdStr = jsonMap.getString("avatarId");
        if (!ChainProjectUtils.isEmpty(avatarIdStr))
            userEntity.setAvatarId(Integer.parseInt(avatarIdStr));
        int insert = userMapper.insert(userEntity);
        if (!ChainProjectUtils.isPositive(insert))
            return false;
        Long id = userEntity.getId();
        userEntity.setUserId(id);
        password = BbsUtils.encryptPassword(password, userEntity.getUserId());
        userEntity.setPassword(password);
        int update = userMapper.update(userEntity);
        if (!ChainProjectUtils.isPositive(update))
            return false;
        return true;
    }

    @Override
    public Map<String, Object> briefInfo(JsonMap jsonMap) {
        Long userId = jsonMap.getParsedLong("userId");
        UserEntity userEntity = userMapper.findByUserId(userId);
        if (ChainProjectUtils.isEmpty(userEntity))
            return null;
        Integer avatarId = userEntity.getAvatarId();
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("avatarId", avatarId);
        return resMap;
    }

    @Override
    public Map<String, Object> lastComment(JsonMap jsonMap) {
        Long userId = jsonMap.getParsedLong("userId");
        return userMapper.findLastComment(userId);
    }

}
