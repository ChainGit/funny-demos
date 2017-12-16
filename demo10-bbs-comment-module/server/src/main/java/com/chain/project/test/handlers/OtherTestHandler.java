package com.chain.project.test.handlers;

import com.chain.project.common.domain.ApiUsage;
import com.chain.project.common.validator.JsonMapValidator;
import com.chain.project.common.directory.Constant;
import com.chain.project.common.domain.JsonMap;
import com.chain.project.common.domain.Result;
import com.chain.project.common.utils.ChainProjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

//测试访问的URL目录是test,均为POST方法
@RequestMapping(value = "/test", method = RequestMethod.POST)
//相当于Controller+ResponseBody
@RestController
public class OtherTestHandler {

    private static Logger logger = LoggerFactory.getLogger(OtherTestHandler.class);

    /***其他测试***/

    //时间的处理：与前端交互默认是以long型，特殊情况下可以使用字符串
    @RequestMapping("/time")
    @ApiUsage(value = "测试时间处理", param = {"t1", "t2"},
            paramDesc = {"长整型long，比如1501556482000", "字符串，比如'2017-03-17 12:08:43'"},
            result = {"t1", "t2", "t3_1", "t3_2", "t3_3", "date1", "date2", "date3"})
    public Result time(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        JsonMapValidator.valid(jsonMap);

        //传递的是long
        String time1 = jsonMap.getFormatDateString("t1");
        System.out.println("time1: " + time1);

        //传递的是满足Constant.TIME_PATTERN的String型时间：如2017-12-01 18:09:27
        String time2 = jsonMap.getString("t2");
        System.out.println("time2: " + time2);

        //转换为Date
        Date date1 = jsonMap.getDate("t1");
        Date date2 = ChainProjectUtils.parseDateFormString(time2);
        Date date3 = new Date();

        JsonMap out = new JsonMap();
        //字符串时间传递
        out.put("t1", time1);
        out.put("t2", time2);
        //传递默认的Date.toString方法
        out.put("t3_1", date3.toString());
        //传递格式化的date
        out.put("t3_2", ChainProjectUtils.toFormatDateString(date3));
        //传递Java8的新API的toString
        out.put("t3_3", date3.toInstant().toString());

        //jackson默认转为long
        out.put("data1", date1);
        out.put("date2", date2);
        out.put("date3", date3);

        return Result.ok(out, Result.SUCCESS);
    }
}
