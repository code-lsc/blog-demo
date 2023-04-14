package com.qf.blog.vo;

import com.qf.blog.entity.UserEntity;
import lombok.Data;

import java.util.List;

/**
 * 帖子vo
 */
@Data
public class CommentVo {

    private UserEntity user; // 用户实体
    private CommentInfoVo commentEntity; // 帖子信息
    private List<CommentVo> replyList; // 评论回复列表
    private Long likeCount; // 点赞数量
    private Integer likeStatus = 0; //点赞状态


}
