package com.qf.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_message")
public class MessageEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;//主键id
    private Integer type;//类型 （1，为点赞消息，2为关注消息，3为评论消息）
    private Integer status;//是否已读
    private Integer uid;//生成该消息的对象id
    private Integer targetId;//消息的接收者
    private Integer entityId;//实体id,点赞的帖子id
    private Date createTime;//消息时间

}
