package com.qf.blog.controller.web;

import com.qf.blog.Service.CommentService;
import com.qf.blog.common.exception.BlogException;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.entity.CommentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private UserHelp userHelp;

    @Autowired
    private CommentService commentService;

    @Value("${blog.host}")
    private String host;

    /**
     * 发表评论的接口
     * @param topicId 帖子id
     * @param commentEntity 前端传来的评论实体
     * @return
     */
    @PostMapping("/add/{topicId}")
    private String add(@PathVariable Integer topicId, CommentEntity commentEntity){

        if (userHelp.get() == null){
            throw new BlogException("用户未登录");
        }

        commentEntity.setUid(userHelp.get().getUid());
        commentEntity.setCreate_time(new Date());
        commentEntity.setStatu(1);
        commentService.save(commentEntity);

        return "redirect:"+host+"topic/detail/"+topicId;

    }

}
