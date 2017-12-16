package com.chain.project.test.handlers;

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
import com.chain.project.test.entities.PersonEntity;
import com.chain.project.test.service.PersonService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 模式：
 * request带参数，response带值
 * request带参数，response不带值
 * request不带参数，response带值
 * request不带参数，response不带值
 * <p>
 * 规定：
 * 1、数据到Controller时一定是有的，即使没有数据，也会创建一个空的JsonMap。
 * 2、返回的Response也一定也是有数据，即使没有返回的数据，也会创建一个空的Result，<b>但仅限方法返回的是Result</b>。
 * 3、如果总的加密配置为false,那么Result中的加密会被忽略，如果总的加密设置为true，那么会根据Result中的加密设置进行加密（默认为true，加密）
 * <p>
 * 约定：
 * 1、加密的数据参数key为"s"，不加密的数据参数key为"ns".
 * 2、先处理s，再处理ns，若s不为空，则ns不再处理.
 */
//测试访问的URL目录是test,均为POST方法
@RequestMapping(value = "/test", method = RequestMethod.POST)
//相当于Controller+ResponseBody
@RestController
public class PersonTestHandler extends BaseHandler {

    private static Logger logger = LoggerFactory.getLogger(PersonTestHandler.class);

    /////////////////////////////////////////////////////////////
    /***以下测试分别在dev和test中进行了测试，prod环境不加载test功能***/
    /////////////////////////////////////////////////////////////

    @Autowired
    private PersonService personService;

    /***根据配置文件决定是否启用加密的机制***/

    //返回加密数据测试
    @RequestMapping("/encrypt")
    @ApiUsage("测试加密和加密的返回数据")
    public Result encrypt(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        System.out.println(jsonMap);
        // throw new RuntimeException("发生错误了1");
        // return null;
        List<PersonEntity> personEntityList = personService.queryListAll();
        if (ChainProjectUtils.isEmpty(personEntityList)) {
            return Result.ok(Result.EMPTY_DATA);
        }
        return Result.ok(personEntityList, Result.SUCCESS);
    }

    //返回不加密数据测试
    @RequestMapping("/plain")
    @ApiUsage("测试加密和不加密的返回数据")
    public Result plain(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        System.out.println(jsonMap);
        // throw new RuntimeException("发生错误了2");
        // return null;
        List<PersonEntity> personEntityList = personService.queryListAll();
        if (ChainProjectUtils.isEmpty(personEntityList)) {
            return Result.ok(Result.EMPTY_DATA).setEncrypt(false);
        }
        return Result.ok(personEntityList, Result.SUCCESS).setEncrypt(false);
    }

    /***增删改查、分页查询、事务（已配置自动开启）的测试***/
    /***注意1：返回均为加密的数据***/
    /***注意2：因为是纯后台，所以采用非REST风格，即所有参数以JSON形式放在body里，且均为POST方式***/

    //查找(规范为GET，这里使用POST)
    @RequestMapping("/find")
    @ApiUsage(value = "测试查找", param = {"id"}, resultDesc = "找到的PersonEntity")
    public Result find(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        System.out.println(jsonMap);
        //Jackson会自动转换
        long id = jsonMap.getLong("id");
        PersonEntity personEntity = personService.findById(id);
        if (ChainProjectUtils.isEmpty(personEntity)) {
            Result.ok(Result.EMPTY_DATA).setEncrypt(Constant.RESPONSE_ENCRYPT_JSON_KEY);
        }
        return Result.ok(personEntity, Result.SUCCESS).setEncrypt(Constant.RESPONSE_ENCRYPT_JSON_KEY)
                .setIgnore(new String[]{"age"});
    }

    //更新
    @RequestMapping("/update")
    @ApiUsage(value = "测试更新", param = {"id"}, optional = {"name", "age"}, resultDesc = "更新结果")
    public Result update(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        System.out.println(jsonMap);
        PersonEntity personEntity = ChainProjectUtils.mapToObject(jsonMap, PersonEntity.class);
        int num = personService.update(personEntity);
        if (ChainProjectUtils.isPositive(num))
            return Result.ok();
        else
            throw new DoRollBack(ErrorDetail.of(ErrorCode.BUSINESS, "更新失败"));
    }

    //增加
    @RequestMapping("/insert")
    @ApiUsage(value = "测试增加", param = {"name", "age"}, resultDesc = "增加结果")
    public Result insert(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        System.out.println(jsonMap);
        PersonEntity personEntity = ChainProjectUtils.mapToObject(jsonMap, PersonEntity.class);
        int num = personService.insert(personEntity);
        if (ChainProjectUtils.isPositive(num))
            return Result.ok();
        else
            throw new DoRollBack(ErrorDetail.of(ErrorCode.BUSINESS, "增加失败"));
    }

    //删除
    @RequestMapping("/delete")
    @ApiUsage(value = "测试删除", param = "id", resultDesc = "删除结果")
    public Result delete(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        System.out.println(jsonMap);
        long id = jsonMap.get("id", Long.class);
        int num = personService.deleteById(id);
        if (ChainProjectUtils.isPositive(num))
            return Result.ok();
        else
            throw new DoRollBack(ErrorDetail.of(ErrorCode.BUSINESS, "删除失败"));
    }

    //分页查询(使用PageHelper)
    @RequestMapping(value = "/page")
    @ApiUsage(value = "测试分页", param = {"page", "rows"},
            paramDesc = {"需要的页数", "每页显示的行数"}, resultDesc = "分页查询的结果")
    public Result page(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        int page = jsonMap.getInteger(Constant.CURRENT_PAGE);
        int rows = jsonMap.getInteger(Constant.EACH_PAGE_ROWS);
        PageInfo<PersonEntity> pageInfo = personService.getPage(page, rows);
        JsonMap outMap = new JsonMap();
        outMap.put(Constant.EACH_PAGE_RECORDS, pageInfo.getList());
        outMap.put(Constant.TOTAL_PAGES, pageInfo.getPages());
        outMap.put(Constant.TOTAL_RECORDS, pageInfo.getTotal());
        if (ChainProjectUtils.isEmpty(outMap))
            return Result.ok(Result.EMPTY_DATA).setEncrypt(Constant.RESPONSE_PLAIN_JSON_KEY);
        return Result.ok(outMap, Result.SUCCESS).setEncrypt(false);
    }

}
