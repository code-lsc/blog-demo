package com.qf.blog.controller.web;

import com.qf.blog.Service.IFollowerService;
import com.qf.blog.Service.ILikeService;
import com.qf.blog.Service.UserService;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private IFollowerService followerService;

    @Autowired
    private ILikeService likeService;

    @Autowired
    private UserHelp userHelp;


    /**
     * 查询用户的账户信息
     * @param uid 用户id
     * @param modelMap
     * @return
     */
    @GetMapping("/info/{uid}")
    public String findUserById(@PathVariable Integer uid, ModelMap modelMap) {
        UserEntity userEntity = userService.findUserById(uid);
        userEntity.setPwd(null);
        Boolean hasFollowed = followerService.findUserFollowerStatus(uid, userHelp.get().getUid());
        Long userFollowerCount = followerService.findUserFollowerCount(uid);
        Long userFansCount = followerService.findUserFansCount(uid);
        Long userLikeCount = likeService.getUserLikeCount(uid);
        modelMap.addAttribute("user", userEntity);
        modelMap.addAttribute("hasFollowed", hasFollowed);
        modelMap.addAttribute("followerCount", userFollowerCount);
        modelMap.addAttribute("fansCount", userFansCount);
        modelMap.addAttribute("userLikeCount", userLikeCount);
        modelMap.addAttribute("pageIndex", 1);
        return "site/profile";
    }


}
