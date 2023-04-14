package com.qf.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 帖子实体
 */
@Data
@TableName("t_invitation")
public class InvitationEntity {


    @TableId(type = IdType.AUTO)
    private Integer id; // 帖子id
    private String title; // 帖子标题
    private String content; // 帖子内容
    private Integer statu; // 帖子状态
    private Integer uid; // 用户id
    private Date createTime; // 创建时间
    private Double score; // 帖子评分
}
