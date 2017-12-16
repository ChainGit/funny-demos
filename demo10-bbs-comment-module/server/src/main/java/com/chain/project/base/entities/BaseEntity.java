package com.chain.project.base.entities;

import java.io.Serializable;
import java.util.Date;


/**
 * 实体类一般遵循下面的规范：
 * 1、根据你的设计，定义一组你需要的私有属性。
 * 2、根据这些属性，创建它们的setter和getter方法。（eclipse等集成开发软件可以自动生成。具体怎么生成？请自行百度。）
 * 3、提供带参数的构造器和无参数的构造器。
 * 4、重写父类中的eauals()方法和hashcode()方法。（如果需要涉及到两个对象之间的比较，这两个功能很重要。）
 * 5、实现序列化并赋予其一个版本号。
 * 6、实体类字段优先使用包装类，做循环等其他操作用基本数据类型。
 * 7、根据具体情况使用ID关联或者对象关联，但建议不要混用。
 */

/**
 * 介绍几个常用的 lombok 注解：
 *
 * @Data: 注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 * @Setter: 注解在属性上；为属性提供 setting 方法
 * @Getter: 注解在属性上；为属性提供 getting 方法
 * @Log4j: 注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
 * @NoArgsConstructor: 注解在类上；为类提供一个无参的构造方法
 * @AllArgsConstructor: 注解在类上；为类提供一个全参的构造方法
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 是否逻辑删除标识，不使用Boolean，0：未删除；1：已删除
     */
    private Integer deleteFlag;

    public BaseEntity() {
    }

    public BaseEntity(Long id, Date createTime, Date updateTime, Integer deleteFlag) {
        this();
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.deleteFlag = deleteFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseEntity{");
        sb.append("id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append('}');
        return sb.toString();
    }


}