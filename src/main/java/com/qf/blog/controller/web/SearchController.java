package com.qf.blog.controller.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.blog.Service.InvitationService;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.entity.InvitationEntity;
import com.qf.blog.vo.IndexDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private InvitationService invitationService;

    /**
     * 帖子搜索
     * @param keyword 关键词
     * @param blogPage
     * @param modelMap
     * @return
     */
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, BlogPage<IndexDataVo> blogPage, ModelMap modelMap) {
        //一个自制的简易中文分词函数
        List<String> key = splitText(keyword);
        blogPage.setSize(5);
        blogPage.setPath("/search?keyword="+keyword);
        /*用动态sql进行like和or的多次拼接，完成分词查找*/
        blogPage = (BlogPage<IndexDataVo>) invitationService.selectInvitationByInput(blogPage,key);

        modelMap.put("page",blogPage);
        return "index";
    }

    //将中文进行分词
    public  List<String> splitText(String text) {
        List<String> substrings = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            for (int j = i + 1; j <= text.length(); j++) {
                String substring = text.substring(i, j);
                substrings.add(substring);
            }
        }

        return substrings;
    }

}
