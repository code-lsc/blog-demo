package com.qf.blog.controller.web;

import com.qf.blog.Service.IFollowerService;
import com.qf.blog.Service.UserService;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.common.exception.BlogException;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.common.utils.R;
import com.qf.blog.entity.UserEntity;
import com.qf.blog.vo.FollowerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/follower")
public class IFollowerController {

    @Autowired
    private UserHelp userHelp;

    @Autowired
    private IFollowerService followerService;

    @Autowired
    private UserService userService;

    /**
     * 点击关注和取消关注接口
     * @param id 被关注人的id
     * @return
     */
    @GetMapping("/click/{id}")
    @ResponseBody
    public R click(@PathVariable Integer id){
        if (userHelp.get() == null){
            throw new BlogException("用户未登录");
        }
        followerService.click(userHelp.get().getUid(),id);
        Boolean hasFollowed = followerService.findUserFollowerStatus(id, userHelp.get().getUid());
        return R.ok().put("hasFollowed",hasFollowed);
    }

    /**
     * 获取关注列表接口
     * @param id 请求人id
     * @param map
     * @return
     */
    @GetMapping("/findFollowerListByUserId/{id}")
    public String findFollowerListByUserId(@PathVariable Integer id, ModelMap map){
        List<FollowerVo> list = followerService.findFollowerListByUserId(id, 1);
        UserEntity userEntity = userService.findUserById(id);
        userEntity.setPwd(null);
        //Boolean hasFollowed = followerService.findUserFollowerStatus(id, userHelp.get().getUid());
        map.addAttribute("followerList",list);
        map.addAttribute("follower",userEntity);
        return "site/followee";
    }

    /**
     * 获取粉丝列表接口
     * @param id 请求人id
     * @param map
     * @return
     */
    @GetMapping("/findFansListByUserId/{id}")
    public String findFansListByUserId(@PathVariable Integer id, ModelMap map){
        List<FollowerVo> list = followerService.findFollowerListByUserId(id, 2);
        UserEntity userEntity = userService.findUserById(id);
        userEntity.setPwd(null);
        map.addAttribute("fansList",list);
        map.addAttribute("follower",userEntity);
        return "site/fans";
    }
}
