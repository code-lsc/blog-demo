package com.qf.blog.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.blog.Service.IFollowerService;
import com.qf.blog.Service.MessageService;
import com.qf.blog.Service.UserService;
import com.qf.blog.common.constants.FollowerConstants;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.entity.MessageEntity;
import com.qf.blog.entity.UserEntity;
import com.qf.blog.vo.FollowerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class IFollowerServiceImpl implements IFollowerService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    /**
     * 点击关注和取消关注
     * @param fid 关注人id
     * @param uid 被关注人id
     */
    @Override
    public void click(Integer fid, Integer uid) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String followerKey = String.format(FollowerConstants.FOLLOWER_KEY,fid);
                String fanskey = String.format(FollowerConstants.FANS_KEY,uid);
                Double score = redisTemplate.opsForZSet().score(followerKey, uid);
                operations.multi();
                if (score == null){
                    redisTemplate.opsForZSet().add(followerKey,uid,System.currentTimeMillis());
                    redisTemplate.opsForZSet().add(fanskey,fid,System.currentTimeMillis());
                    //给被关注的用户发送一个消息
                        MessageEntity message = new MessageEntity();
                        message.setType(2);/*表示点赞消息*/
                        message.setUid(fid);
                        message.setTargetId(uid);
                        message.setCreateTime(new Date());
                        messageService.save(message);

                }else {
                    redisTemplate.opsForZSet().remove(followerKey,uid);
                    redisTemplate.opsForZSet().remove(fanskey,fid);
                    //取消关注，删除消息
                    MessageEntity message = new MessageEntity();
                    message.setType(2);/*表示点赞消息*/
                    message.setUid(fid);
                    message.setTargetId(uid);
                    QueryWrapper<MessageEntity> wrapper = new QueryWrapper(message);
                    messageService.remove(wrapper);
                    System.out.println("================");

                }
                operations.exec();
                return null;
            }
        });

    }

    /**
     *
     * @param uid 被关注用户id
     * @param fid 关注人id
     * @return
     */
    @Override
    public Boolean findUserFollowerStatus(Integer uid, Integer fid) {
        String followerKey = String.format(FollowerConstants.FOLLOWER_KEY,fid);
        return redisTemplate.opsForZSet().score(followerKey,uid) != null?true:false;
    }

    /**
     * 该用户的关注数量
     * @param uid
     * @return
     */
    @Override
    public Long findUserFollowerCount(Integer uid) {
        String followerKey = String.format(FollowerConstants.FOLLOWER_KEY,uid);
        return redisTemplate.opsForZSet().size(followerKey);
    }

    @Override
    public Long findUserFansCount(Integer uid) {
        String fansKey = String.format(FollowerConstants.FANS_KEY,uid);
        return redisTemplate.opsForZSet().size(fansKey);

    }

    @Override
    public List<FollowerVo> findFollowerListByUserId(Integer uid, Integer type) {
        String key = null;
        if (type == 1 ){
            //看我关注了谁
            key = String.format(FollowerConstants.FOLLOWER_KEY,uid);
        }else if (type == 2){
            //看谁关注了我
            key = String.format(FollowerConstants.FANS_KEY,uid);
        }
        Set members = redisTemplate.opsForZSet().range(key,0,-1);
        List<FollowerVo> list = new ArrayList<>();

        for (Object member : members) {
            FollowerVo followerVo = new FollowerVo();
            UserEntity userEntity = userService.findUserById(Integer.valueOf(member.toString()));
            followerVo.setUserId(userEntity.getId());
            followerVo.setUserName(userEntity.getUsername());
            followerVo.setHeadUrl(userEntity.getHeadUrl());
            long time = redisTemplate.opsForZSet().score(key, member).longValue();
            followerVo.setCreateTime(new Date(time));
            list.add(followerVo);
        }

        return list;
    }
}
