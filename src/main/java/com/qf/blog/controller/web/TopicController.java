package com.qf.blog.controller.web;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qf.blog.Service.*;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.common.exception.BlogException;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.common.utils.R;
import com.qf.blog.entity.InvitationEntity;
import com.qf.blog.entity.UserEntity;
import com.qf.blog.vo.CommentVo;
import com.qf.blog.vo.LikeVo;
import com.qf.blog.vo.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private UserHelp userHelp;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ILikeService likeService;

    @Autowired
    private MessageService messageService;

    /**
     * 发表帖子接口
     * @param invitationEntity 前端接收的一个帖子实体
     * @return
     */
    @PostMapping("/publish")
    @ResponseBody
    public R publish(@RequestBody InvitationEntity invitationEntity){
        UserToken userToken = userHelp.get();
        if (userToken == null){
            throw new BlogException("用户未登录");
        }
        invitationEntity.setStatu(1);
        invitationEntity.setUid(userToken.getUid());
        invitationEntity.setCreateTime(new Date());
        invitationService.save(invitationEntity);
        return R.ok();
    }

    /**
     * 查看帖子详情接口
     * @param commentVoPage
     * @param id 帖子id
     * @param modelMap
     * @return
     */
    @GetMapping("/detail/{id}")
    public String detail(BlogPage<CommentVo> commentVoPage,
                         @PathVariable Integer id, ModelMap modelMap){
        InvitationEntity invitation = invitationService.getById(id);
        UserEntity user = userService.getById(invitation.getUid());
        commentVoPage.setSize(4);
        commentVoPage=commentService.selectCommentListByTid(commentVoPage,id);//我自己新加的
        LikeVo likeVo = new LikeVo();
        likeVo.setEntityType(1);
        likeVo.setEntityId(id);
        if (userHelp.get()!=null){
            likeVo.setUserId(userHelp.get().getUid());
        }

        Long likeCount = likeService.likeCount(likeVo);
        Boolean likeStatus = likeService.likeStatus(likeVo);

        commentVoPage.setPath("/topic/detail"+id);
        modelMap.addAttribute("topic",invitation);
        modelMap.addAttribute("user",user);
        modelMap.addAttribute("page",commentVoPage);
        modelMap.addAttribute("likeCount",likeCount);
        modelMap.addAttribute("likeStatus",likeStatus?1:0);


        return "site/discuss-detail";
    }

    /**
     * 删除帖子接口
     * @param id 帖子id
     * @return
     */
    @GetMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable Integer id){
        System.out.println(id);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id",id);
        updateWrapper.set("statu",0);
        invitationService.update(updateWrapper);
        return R.ok();
    }

    /**
     * 修改帖子接口
     * @param id 帖子id
     * @param modelMap
     * @return
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable Integer id,ModelMap modelMap){
        InvitationEntity invitation = invitationService.getById(id);
        modelMap.addAttribute("title",invitation.getTitle());
        modelMap.addAttribute("content",invitation.getContent());
        return "site/discuss-publish";
    }

}
