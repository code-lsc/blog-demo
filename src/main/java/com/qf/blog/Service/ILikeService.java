package com.qf.blog.Service;

import com.qf.blog.vo.LikeVo;

public interface ILikeService {

    //点赞业务
    void clickLike(LikeVo likeVo);

    //查询点赞数量
    Long likeCount(LikeVo likeVo);

    //点赞状态
    Boolean likeStatus(LikeVo likeVo);

    //查询用户点赞数量
    Long getUserLikeCount(Integer userId);


}
