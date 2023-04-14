package com.qf.blog.controller.web;

import com.qf.blog.Service.MessageService;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.common.exception.BlogException;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.entity.MessageEntity;
import com.qf.blog.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/letter")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserHelp userHelp;

    /**
     * 查看消息和读取消息的接口
     * @param blogPage
     * @param modelMap
     * @return
     */
    @GetMapping("/list")
    public String selectMessageList(BlogPage<MessageVo> blogPage, ModelMap modelMap) {
        if (userHelp.get() == null) {
            throw new BlogException("用户未登录");
        }
        blogPage.setSize(2);
        blogPage = (BlogPage<MessageVo>) messageService.selectMessageByUserId(blogPage, userHelp.get().getUid());
        modelMap.put("page", blogPage);
        //只要访问过这个接口，就代表消息已读
        List<MessageEntity> list = new ArrayList<>();
        for (MessageVo record : blogPage.getRecords()) {
            MessageEntity message = new MessageEntity();
            message.setId(record.getId());
            message.setStatus(1);
            list.add(message);
        }
        messageService.updateBatchById(list, list.size());
        return "site/letter";
    }


}
