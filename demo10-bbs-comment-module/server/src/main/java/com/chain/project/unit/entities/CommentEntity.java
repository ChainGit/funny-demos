package com.chain.project.unit.entities;

import com.chain.project.base.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 评论表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class CommentEntity extends BaseEntity {

    /**
     * 这条评论的ID
     */
    private Long commentId;
    /**
     * 评论的纯文字内容
     */
    private String text;
    /**
     * 这条评论的用户（对应User表中的userId）
     */
    private Long userId;
    /**
     * 父评论（对应Comment表的commentId）
     */
    private Long parentId;
}
