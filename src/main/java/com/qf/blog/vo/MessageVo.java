package com.qf.blog.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MessageVo {

    private Integer id;//主键id
    private Integer uid;//触发消息的用户id
    private Integer entityId;//实体id，存储喜欢的帖子id
    private String userName;//触发消息的用户名
    private String headUrl;//出发消息的用户头像
    private String invitationName;//帖子标题
    private Integer type;//消息类型（1.关注，2.点赞）
    private Integer status;//状态（是否已读）
    private Date createTime;//创建时间
}
