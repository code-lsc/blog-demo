package com.qf.blog.Service;

import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.vo.FollowerVo;

import java.util.List;

public interface IFollowerService {

    //关注、取消关注
    void click(Integer fid, Integer uid);
    //关注状态
    Boolean findUserFollowerStatus(Integer uid, Integer fid);
    //查看关注人数量
    Long findUserFollowerCount(Integer uid);
    //查看粉丝数量
    Long findUserFansCount(Integer uid);
    //查看关注列表
    List<FollowerVo> findFollowerListByUserId(Integer uid, Integer type);

}
