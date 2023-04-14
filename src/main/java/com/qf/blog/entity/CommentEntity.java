package com.qf.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 评论实体
 */
@Data
@TableName("t_comment")
public class CommentEntity {

    @TableId(type = IdType.AUTO)
    private Integer id; // 评论id
    private Integer uid; // 用户id(发评论的人的id)
    private Integer entityType; // 实体类型  1(对帖子进行评论) 2(对评论的回复）
    private Integer entityId; // 实体id (被回复的目标id ,可以是帖子的id ,也可以是评论的id)
    private Integer targetId; // 目标id（被评论的用户id,一般用于二级评论时×××回复了×××，通过此id可以找到目标人的名字）
    private String content; // 评论内容
    private Integer statu; // 状态
    private Date create_time; // 创建时间
}
