package com.qf.blog.vo;

import lombok.Data;

import java.util.Date;

/**
 * 评论数据Vo
 */
@Data
public class CommentInfoVo {

    private Integer id; // 评论id
    private Integer uid; // 用户id
    private Integer entityType;
    private Integer entityId; // 可以对帖子评论，也可以对评论进行评论
    private Integer targetId; // 目标id
    private String targetName;
    private String content;
    private Integer statu;
    private Date createTime;

}
