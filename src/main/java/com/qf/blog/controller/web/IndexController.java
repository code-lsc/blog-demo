package com.qf.blog.controller.web;


import com.qf.blog.Service.InvitationService;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.vo.IndexDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private InvitationService invitationService;

    /**
     * 查询首页的帖子数据接口
     * @param blogPage
     * @param modelMap
     * @return
     */
    @GetMapping("/")
    public String index(BlogPage<IndexDataVo> blogPage, ModelMap modelMap){
        //设置一页数据的条数
        blogPage.setSize(5);
        blogPage = (BlogPage<IndexDataVo>) invitationService.indexPage(blogPage);
        blogPage.setPath("/");
        modelMap.put("page",blogPage);
        return "index";
    }

    /**
     * 最新和最热帖子接口
     * @param id 前端传来的orderMode的id值
     * @param blogPage
     * @param modelMap
     * @return
     */
    @GetMapping("/index")
    public String orderByTime(@RequestParam("orderMode") Integer id, BlogPage<IndexDataVo> blogPage,
                              ModelMap modelMap) {
        //最新的帖子
        if (id == 0) {
            index(blogPage, modelMap);
        }else {
            //最热的帖子，按点赞数来排序
            blogPage.setSize(5);
            blogPage.setPath("/index?orderMode=1");
            blogPage = (BlogPage<IndexDataVo>) invitationService.indexPage(blogPage);
            List<IndexDataVo> records = blogPage.getRecords();
            records.sort((o1, o2) -> o2.getInvitationDataEntity().getLikes() - o1.getInvitationDataEntity().getLikes());
            modelMap.put("page",blogPage);
        }
        return "index";
    }
}
