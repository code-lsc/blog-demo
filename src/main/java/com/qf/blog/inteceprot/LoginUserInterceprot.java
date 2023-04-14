package com.qf.blog.inteceprot;

import com.qf.blog.Service.MessageService;
import com.qf.blog.common.constants.UserConstants;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.common.utils.CookieUtils;
import com.qf.blog.vo.UserToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
@Slf4j
public class LoginUserInterceprot implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserHelp userHelp;

    @Autowired
    private MessageService messageService;

    //controller前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getValue(request);
        if (!ObjectUtils.isEmpty(token)){
            UserToken  userToken= (UserToken) redisTemplate.opsForValue().get(
                    String.format(UserConstants.USER_LOGIN_TOKEN, token));
            //userToken不能为空且token没有过期
            if (userToken != null && userToken.getTtl().after(new Date())){
                userHelp.set(userToken);

            }else {
                log.debug("token不存在或过期");
            }
        }

        return true;
    }

    //最后执行,一般用来销毁资源
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userHelp.remove();
    }

    //视图解析之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserToken userToken = userHelp.get();
        if (userToken != null && modelAndView != null){
            modelAndView.addObject("loginUser",userToken);
            Integer count = messageService.selectNoReadCountById(userHelp.get().getUid());
            modelAndView.addObject("allUnreadCount", count);
        }
    }
}
