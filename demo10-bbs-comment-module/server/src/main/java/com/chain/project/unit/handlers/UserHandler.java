package com.chain.project.unit.handlers;

import com.chain.project.base.handlers.BaseHandler;
import com.chain.project.common.directory.Constant;
import com.chain.project.common.domain.ApiUsage;
import com.chain.project.common.domain.JsonMap;
import com.chain.project.common.domain.Result;
import com.chain.project.common.exception.DoRollBack;
import com.chain.project.common.exception.ErrorCode;
import com.chain.project.common.exception.ErrorDetail;
import com.chain.project.common.utils.ChainProjectUtils;
import com.chain.project.common.validator.JsonMapValidator;
import com.chain.project.unit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping(value = "/user", method = RequestMethod.POST)
//相当于Controller+ResponseBody
@RestController
//允许浏览器跨域访问
@CrossOrigin
public class UserHandler extends BaseHandler {

    @Autowired
    private UserService userService;

    @RequestMapping("/signin")
    @ApiUsage(value = "用户登陆", param = {"name", "password"},
            result = {"userId"})
    public Result signIn(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        Map<String, Object> resMap = userService.signIn(jsonMap);

        if (!ChainProjectUtils.isEmpty(resMap))
            return Result.ok(resMap, Result.SUCCESS);
        return Result.fail(ErrorDetail.of(ErrorCode.BUSINESS, "用户登陆失败"));
    }


    @RequestMapping("/signup")
    @ApiUsage(value = "用户注册", param = {"name", "password"}, resultOptional = {"avatarId"},
            resultDesc = "由Result的success来判断")
    public Result signUp(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        boolean res = userService.signUp(jsonMap);

        if (res)
            return Result.ok(Result.SUCCESS);
        throw new DoRollBack(ErrorDetail.of(ErrorCode.BUSINESS, "用户注册失败"));
    }

    @RequestMapping("/briefInfo")
    @ApiUsage(value = "获取用户信息", param = {"userId"},
            result = {"avatarId"})
    public Result briefInfo(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        Map<String, Object> resMap = userService.briefInfo(jsonMap);

        if (!ChainProjectUtils.isEmpty(resMap))
            return Result.ok(resMap, Result.SUCCESS);
        return Result.fail(ErrorDetail.of(ErrorCode.BUSINESS, "获取用户信息失败"));
    }

    @RequestMapping("/lastComment")
    @ApiUsage(value = "获取用户最后发言的时间", param = {"userId"}, result = {"lastComment"})
    public Result lastComment(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        Map<String, Object> resMap = userService.lastComment(jsonMap);

        if (!ChainProjectUtils.isEmpty(resMap))
            return Result.ok(resMap, Result.SUCCESS);
        return Result.fail(ErrorDetail.of(ErrorCode.BUSINESS, "获取用户最后发言的时间失败"));
    }
}
