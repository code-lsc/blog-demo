package com.qf.blog.vo;

import lombok.Data;

@Data
public class LikeVo {

    private Integer entityType; // 3（对人点赞） 2（对帖子点赞）1（对评论点赞）
    private Integer entityId; //实体id（帖子，或评论）
    private Integer entityUserId;//被点赞的用户id
    private Integer userId;//点赞的用户id

}
