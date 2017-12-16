package com.chain.project.unit.validator;

import com.chain.project.common.utils.ChainProjectUtils;
import com.chain.project.unit.domain.BbsConstant;
import com.chain.project.unit.domain.BbsErrorCode;
import com.chain.project.unit.entities.UserEntity;
import com.chain.project.unit.mapper.CommentMapper;
import com.chain.project.unit.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("validatorService")
public class ValidatorServiceImpl implements ValidatorService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean isUserExist(Long userId) {
        if (ChainProjectUtils.isEmpty(userId))
            return false;
        UserEntity userEntity = userMapper.findByUserId(userId);
        if (!ChainProjectUtils.isEmpty(userEntity))
            return true;
        return false;
    }

    @Override
    public int checkText(Long userId, String text) {
        if (ChainProjectUtils.isEmpty(text))
            return BbsErrorCode.TEXT_EMPTY;
        if (text.length() < BbsConstant.TEXT_MIN)
            return BbsErrorCode.TEXT_SHORT;
        String[] illegalWords = getIllegalWords();
        for (int i = 0; i < illegalWords.length; i++) {
            String illegal = illegalWords[i];
            if (text.contains(illegal))
                return BbsErrorCode.TEXT_ILLEGAL;
        }
        boolean tooFast = isTooFast(userId);
        if (tooFast)
            return BbsErrorCode.TEXT_FAST;
        return BbsConstant.TEXT_OK;
    }

    @Override
    public boolean isTooFast(Long userId) {
        Map<String, Object> resMap = userMapper.findLastComment(userId);
        Date lastComment = (Date) resMap.get("lastComment");
        if (ChainProjectUtils.isEmpty(lastComment))
            return false;
        Date date = new Date();
        long minutes = ChainProjectUtils.minutesOfTwo(lastComment, date);
        if (minutes < BbsConstant.TEXT_LIMIT)
            return true;
        return false;
    }

    private static String[] illagals;

    private String[] getIllegalWords() {
        //应当在配置文件中配置后读取出来
        if (ChainProjectUtils.isEmpty(illagals))
            illagals = new String[]{"fuck"};
        return illagals;
    }

}
