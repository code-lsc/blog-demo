package com.qf.blog.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.blog.Service.ILikeService;
import com.qf.blog.Service.MessageService;
import com.qf.blog.common.constants.LikeConstatns;
import com.qf.blog.common.status.BlogStatus;
import com.qf.blog.entity.MessageEntity;
import com.qf.blog.vo.LikeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ILikeServiceImpl implements ILikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MessageService messageService;

    /**
     * 点赞和取消
     *
     * @param likeVo
     */
    @Override
    public void clickLike(LikeVo likeVo) {

        //redis 操作比较频繁，创建一个事务管理
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //对帖子点赞和评论的key
                String key = String.format(LikeConstatns.LIKE_TOPIC_KEY, likeVo.getEntityType(), likeVo.getEntityId());
                //对人点赞的key
                String userLikeKey = String.format(LikeConstatns.USER_LIKE_KEY, likeVo.getEntityUserId());
                //判断当前用户是否点过赞
                Boolean member = redisTemplate.opsForSet().isMember(key, likeVo.getUserId());
                //开启事务
                operations.multi();
                //如果用户点过赞，取消点赞
                if (member) {
                    redisTemplate.opsForSet().remove(key, likeVo.getUserId());
                    //被取消的点赞用户点赞数量减少
                    redisTemplate.opsForValue().decrement(userLikeKey);
                    /*删除这条消息*/
                    if (likeVo.getEntityType() == 1){
                        MessageEntity message = new MessageEntity();
                        message.setType(1);/*表示点赞消息*/
                        message.setUid(likeVo.getUserId());
                        message.setTargetId(likeVo.getEntityUserId());
                        message.setEntityId(likeVo.getEntityId());
                        QueryWrapper<MessageEntity> wrapper = new QueryWrapper(message);
                        messageService.remove(wrapper);
                    }

                } else {
                    //点赞
                    redisTemplate.opsForSet().add(key, likeVo.getUserId());
                    redisTemplate.opsForValue().increment(userLikeKey);
                    //给被点赞用户发送一个消息
                    //表示给帖子点赞
                    if (likeVo.getEntityType() == 1 && likeVo.getEntityUserId()!=likeVo.getUserId()){
                        MessageEntity message = new MessageEntity();
                        message.setType(1);/*表示点赞消息*/
                        message.setUid(likeVo.getUserId());
                        message.setTargetId(likeVo.getEntityUserId());
                        message.setEntityId(likeVo.getEntityId());
                        message.setCreateTime(new Date());
                        messageService.save(message);
                    }
                }
                operations.exec();
                return null;
            }
        });
    }

    /**
     * 获取帖子点赞数量
     *
     * @param likeVo
     * @return
     */
    @Override
    public Long likeCount(LikeVo likeVo) {
        String key = String.format(LikeConstatns.LIKE_TOPIC_KEY, likeVo.getEntityType(), likeVo.getEntityId());
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 判断被点赞的状态
     *
     * @param likeVo
     * @return
     */
    @Override
    public Boolean likeStatus(LikeVo likeVo) {
        String key = String.format(LikeConstatns.LIKE_TOPIC_KEY, likeVo.getEntityType(), likeVo.getEntityId());
        return redisTemplate.opsForSet().isMember(key, likeVo.getUserId());
    }

    /**
     * 获取用户点赞数量
     *
     * @param userId
     * @return
     */
    @Override
    public Long getUserLikeCount(Integer userId) {
        String userLikeKey = String.format(LikeConstatns.USER_LIKE_KEY, userId);
        Object o = redisTemplate.opsForValue().get(userLikeKey);
        if (o == null) {
            //用户没有被点赞，返回0
            return 0L;
        }
        return Long.valueOf(o.toString());
    }
}
