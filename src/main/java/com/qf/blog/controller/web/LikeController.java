package com.qf.blog.controller.web;

import com.qf.blog.Service.ILikeService;
import com.qf.blog.common.exception.BlogException;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.common.utils.R;
import com.qf.blog.vo.LikeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/like")
@RestController
public class LikeController {

    @Autowired
    private ILikeService likeService;

    @Autowired
    private UserHelp userHelp;

    /**
     * 点击喜欢和取消喜欢接口
     * @param likeVo 前端传来的喜欢vo
     * @return
     */
    @PostMapping("/clickLike")
    public R clickLike(@RequestBody LikeVo likeVo) {
        //点赞
        if (userHelp.get() == null) {
            throw new BlogException("用户未登录");
        }
        likeVo.setUserId(userHelp.get().getUid());
        likeService.clickLike(likeVo);
        //统计实体(帖子，回复）点赞数量
        Long likeCount = likeService.likeCount(likeVo);
        //查询点赞状态
        Boolean likeStatus = likeService.likeStatus(likeVo);
        //将数据返回给前端

        return R.ok().put("likeCount",likeCount).put("likeStatus",likeStatus?1:0);
    }

}
