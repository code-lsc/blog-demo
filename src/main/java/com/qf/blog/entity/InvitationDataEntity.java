package com.qf.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 帖子详情实体
 */
@Data
@TableName("t_invitation_data")
public class InvitationDataEntity {

    @TableId(type = IdType.AUTO)
    private Integer id; // 帖子id
    private Integer tid; // 帖子id
    private Integer pv; // 浏览量
    private Integer likes; // 点赞量
    private Integer comments; // 评论量
    private Integer collect; // 收藏量
}
