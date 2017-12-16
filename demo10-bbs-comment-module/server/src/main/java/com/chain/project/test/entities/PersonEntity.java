package com.chain.project.test.entities;

import com.chain.project.base.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 实体类一般遵循下面的规范：
 * 1、根据你的设计，定义一组你需要的私有属性。
 * 2、根据这些属性，创建它们的setter和getter方法。（eclipse等集成开发软件可以自动生成。具体怎么生成？请自行百度。）
 * 3、提供带参数的构造器和无参数的构造器。
 * 4、重写父类中的eauals()方法和hashcode()方法。（如果需要涉及到两个对象之间的比较，这两个功能很重要。）
 * 5、实现序列化并赋予其一个版本号。
 * 6、实体类字段优先使用包装类，做循环,setter等其他业务操作用可以使用基本数据类型。
 * 7、根据具体情况使用ID关联或者对象关联，但建议不要混用。
 */
@Repository
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity extends BaseEntity {

    private String name;
    private Integer age;


}
