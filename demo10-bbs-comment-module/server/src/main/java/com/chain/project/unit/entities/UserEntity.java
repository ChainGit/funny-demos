package com.chain.project.unit.entities;

import com.chain.project.base.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 用户表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class UserEntity extends BaseEntity {

    /**
     * 用户id，不同于主键id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码（数据库中存储的是md5加密后的）
     */
    private String password;
    /**
     * 头像的ID，前台提供头像
     */
    private Integer avatarId;
    /**
     * 最后一次评论的时间
     */
    private Date lastComment;
}
