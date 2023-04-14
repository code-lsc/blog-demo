package com.qf.blog.controller.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.blog.Service.*;
import com.qf.blog.common.constants.UserConstants;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.common.exception.BlogException;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.common.utils.R;
import com.qf.blog.entity.UserEntity;
import com.qf.blog.vo.IndexDataVo;
import com.qf.blog.vo.MessageVo;
import com.qf.blog.vo.ReplyVo;
import com.qf.blog.vo.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private IFollowerService followerService;

    @Autowired
    private ILikeService likeService;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private UserHelp userHelp;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${blog.host}")
    private String host;

    /**
     * 注册接口
     *
     * @param userEntity 接收前端传来的用户实体数据
     * @return 返回结果状态
     */
    @PostMapping("/register")
    @ResponseBody
    public R register(@RequestBody UserEntity userEntity) {
        userService.register(userEntity);
        return R.ok();
    }

    /**
     * 用户激活接口
     *
     * @param code 激活码
     * @return
     */
    @GetMapping("/activateUser/{code}")
    @ResponseBody
    public String activityUser(@PathVariable String code) {
        if (!ObjectUtils.isEmpty(code)) {
            Integer integer = userService.activateUser(code);
            if (integer > 0)
                return "激活成功!";
        }
        return "激活失败!";
    }

    /**
     * 登录接口
     *
     * @param userEntity 用户实体
     * @param response
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public R login(@RequestBody UserEntity userEntity, HttpServletResponse response) {
        UserToken token = userService.login(userEntity.getUsername(), userEntity.getPwd());
        //将token存入cookie
        Cookie cookie = new Cookie(UserConstants.TOKEN_COOKIE_KEY, token.getToken());
        cookie.setMaxAge(UserConstants.USER_DEFAULE_TIMEOUT);
        cookie.setPath("/");
        response.addCookie(cookie);
        return R.ok();
    }

    /**
     * 退出登录接口
     *
     * @param token    前端cookie中传来的用户token
     * @param response
     * @return
     */
    @GetMapping("/logout")
    @ResponseBody
    public R logout(@CookieValue(name = UserConstants.TOKEN_COOKIE_KEY, defaultValue = "")
                            String token, HttpServletResponse response) {
        userService.logout(token);
        Cookie cookie = new Cookie(UserConstants.TOKEN_COOKIE_KEY, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return R.ok();
    }

    /**
     * 修改密码接口
     *
     * @param oldPassword  原始密码
     * @param newPassword  新密码
     * @param newPassword2 确认新密码
     * @param response
     */
    @PostMapping("/password")
    public void updatePassword(String oldPassword, String newPassword, String newPassword2,
                               HttpServletResponse response) {
        userService.updatePassword(oldPassword, newPassword, newPassword2);
        Cookie cookie = new Cookie(UserConstants.TOKEN_COOKIE_KEY, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            response.sendRedirect("/site/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 用户详情接口
     * @param uid 用户id
     * @param modelMap
     * @return
     */
    @GetMapping("/profile/{uid}")
    public String findUserById(@PathVariable Integer uid, ModelMap modelMap) {
        UserEntity userEntity = userService.findUserById(uid);
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

    /**
     * 查询某个用户的所有帖子
     * @param blogPage
     * @param modelMap
     * @param uid 用户id
     * @return
     */
    @GetMapping("/discuss/{uid}")
    public String selectInvitationByUser(BlogPage<IndexDataVo> blogPage, ModelMap modelMap,
                                         @PathVariable Integer uid) {
        blogPage.setSize(3);
        blogPage = (BlogPage<IndexDataVo>) invitationService.selectInvitationByUserId(blogPage, uid);
        UserEntity userEntity = userService.findUserById(uid);
        userEntity.setPwd(null);
        modelMap.put("page", blogPage);
        modelMap.put("pageIndex", 2);
        modelMap.put("user", userEntity);
        return "site/profile";
    }


    /**
     * 上传头像接口
     * @param file 前端传来的头像文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public R uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Resource resource = resourceLoader.getResource("classpath:static/images");
            File fileResource = resource.getFile();
            Path path = Paths.get(fileResource + "/" + fileName);
            // 将文件写入磁盘
            Files.write(path, bytes);

            // 修改数据库
            String url = host + "images/" + fileName;
            userService.updateHeadUrl(userHelp.get().getUid(), url);
            /*修改redis内的数据*/
            String key = String.format(UserConstants.USER_INFO_KEY,userHelp.get().getUid());
            UserEntity userEntity = (UserEntity) redisTemplate.opsForValue().get(key);
            if (userEntity != null){
                userEntity.setHeadUrl(url);
                redisTemplate.opsForValue().set(key,userEntity,1, TimeUnit.DAYS);
            }

        }
        return R.ok();
    }

    /**
     * 获取该用户的评论回复
     * @param blogPage
     * @param modelMap
     * @param id 用户id
     * @return
     */
    @GetMapping("/comment/{id}")
    public String selectReply(BlogPage<ReplyVo> blogPage, ModelMap modelMap,@PathVariable Integer id){
        if (userHelp.get() == null) {
            throw new BlogException("用户未登录");
        }
        blogPage.setSize(3);
        blogPage = (BlogPage<ReplyVo>) commentService.selectReplyById(blogPage, id);
        UserEntity userEntity = userService.findUserById(id);
        modelMap.put("page", blogPage);
        modelMap.put("pageIndex", 3);
        modelMap.put("user", userEntity);
        return "site/profile";
    }
}

