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
import com.chain.project.unit.domain.BbsConstant;
import com.chain.project.unit.domain.BbsErrorCode;
import com.chain.project.unit.service.CommentService;
import com.chain.project.unit.validator.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;


@RequestMapping(value = "/comment", method = RequestMethod.POST)
//相当于Controller+ResponseBody
@RestController
//允许浏览器跨域访问
@CrossOrigin
public class CommentHandler extends BaseHandler {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ValidatorService validatorService;

    @RequestMapping("/main/add")
    @ApiUsage(value = "添加主评论", param = {"userId", "text"}, resultDesc = "通过Result的success判断")
    public Result addMain(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        //判断用户是否存在
        Long userId = jsonMap.getParsedLong("userId");
        if (!validatorService.isUserExist(userId))
            return Result.fail(ErrorDetail.of(ErrorCode.BUSINESS, "用户不存在"));

        //判断用户输入的评论内容
        String text = jsonMap.getParsedString("text");
        int code = validatorService.checkText(userId, text);
        if (code != BbsConstant.TEXT_OK) {
            String msg = BbsErrorCode.getErrorMsg(code);
            ErrorDetail error = ErrorDetail.of(code, msg);
            return Result.fail(error);
        }

        boolean res = commentService.addMain(jsonMap);

        if (res)
            return Result.ok(Result.SUCCESS);
        throw new DoRollBack(ErrorDetail.of(ErrorCode.BUSINESS, "添加主评论失败"));
    }

    @RequestMapping("/main/delete")
    @ApiUsage(value = "删除主评论", param = {"userId", "commentId"},
            resultDesc = "通过Result的success判断")
    public Result deleteMain(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        //判断用户是否存在
        Long userId = jsonMap.getParsedLong("userId");
        if (!validatorService.isUserExist(userId))
            return Result.fail(ErrorDetail.of(ErrorCode.BUSINESS, "用户不存在"));

        boolean res = commentService.deleteMain(jsonMap);

        if (res)
            return Result.ok(Result.SUCCESS);
        throw new DoRollBack(ErrorCode.BUSINESS + "删除主评论失败");
    }

    @RequestMapping("/main/find")
    @ApiUsage(value = "分页获得主评论列表（不包括多级回复）", param = {"rows", "page"}, resultDesc = "List(each)",
            result = {"records", "total", "time", "userId", "userName", "userAvatarId", "commentId", "text"})
    public Result findMain(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        Map<String, Object> resMap = commentService.findMain(jsonMap);

        if (!ChainProjectUtils.isEmpty(resMap))
            return Result.ok(resMap, Result.SUCCESS);
        return Result.fail(ErrorDetail.of(ErrorCode.BUSINESS, "分页获得主评论列表失败"));
    }


    @RequestMapping("/reply/add")
    @ApiUsage(value = "添加回复评论", param = {"userId", "parentCommentId", "text"},
            resultDesc = "通过Result的success判断")
    public Result addReply(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        //判断用户是否存在
        Long userId = jsonMap.getParsedLong("userId");
        if (!validatorService.isUserExist(userId))
            return Result.fail(ErrorDetail.of(ErrorCode.BUSINESS, "用户不存在"));

        boolean res = commentService.addReply(jsonMap);

        if (res)
            return Result.ok(Result.SUCCESS);
        throw new DoRollBack(ErrorDetail.of(ErrorCode.BUSINESS, "添加回复评论失败"));
    }


    @RequestMapping("/reply/delete")
    @ApiUsage(value = "删除回复评论(同时删除子评论)", param = {"userId", "commentId"},
            resultDesc = "通过Result的success判断")
    public Result deleteReply(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        //判断用户是否存在
        Long userId = jsonMap.getParsedLong("userId");
        if (!validatorService.isUserExist(userId))
            return Result.fail(ErrorDetail.of(ErrorCode.BUSINESS, "用户不存在"));

        boolean res = commentService.deleteReply(jsonMap);

        if (res)
            return Result.ok(Result.SUCCESS);
        throw new DoRollBack(ErrorDetail.of(ErrorCode.BUSINESS, "删除回复评论失败"));
    }


    @RequestMapping("/reply/find")
    @ApiUsage(value = "获得多级回复评论列表", param = {"parentCommentId"}, resultDesc = "List",
            result = {"time", "userId", "userName", "userAvatarId", "commentId", "text"})
    public Result findReply(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) throws IOException {
        JsonMapValidator.valid(jsonMap);

        Object reply = commentService.findReply(jsonMap);

        if (!ChainProjectUtils.isEmpty(reply))
            return Result.ok(reply, Result.SUCCESS);
        return Result.fail(ErrorDetail.of(ErrorCode.BUSINESS, "获得多级评论列表失败"));
    }
}
