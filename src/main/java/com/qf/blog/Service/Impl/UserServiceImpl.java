package com.qf.blog.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.blog.Service.UserService;
import com.qf.blog.common.constants.UserConstants;
import com.qf.blog.common.exception.BlogException;
import com.qf.blog.common.exception.EmailException;
import com.qf.blog.common.executor.ExecutorUtils;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.common.status.BlogStatus;
import com.qf.blog.common.utils.EmailUtils;
import com.qf.blog.common.utils.PasswordUtils;
import com.qf.blog.common.utils.UUIDUtils;
import com.qf.blog.dao.UserDao;
import com.qf.blog.dto.Email;
import com.qf.blog.entity.UserEntity;
import com.qf.blog.vo.UserToken;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private EmailUtils emailUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserHelp userHelp;

    /**
     * 注册接口
     * @param userEntity
     */
    @Override
    public void register(UserEntity userEntity) {
        //判断当前邮箱是否被注册
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email", userEntity.getEmail());
        UserEntity dbUserEntity = baseMapper.selectOne(queryWrapper);
        if (dbUserEntity != null) {
            //查询不为空，邮箱被注册
            throw new EmailException("该邮箱已经被注册");
        }

        //如果邮箱没有被注册，开始设置用户信息
        //设置邮箱状态未激活
        userEntity.setStatu(BlogStatus.USER_UN_ACTIVATE.getCode());
        //给用户设置激活码
        userEntity.setActivateCode(UUIDUtils.getUUid());
        //设置创建时间
        userEntity.setCreateTime(new Date());
        //设置加密密码
        userEntity.setPwd(PasswordUtils.encode(userEntity.getPwd()));
        //向数据库插入数据
        baseMapper.insert(userEntity);
        //将信息保存至redis中
        stringRedisTemplate.opsForValue().set(
                //key
                String.format(UserConstants.ACTIVATEKEY, userEntity.getActivateCode()),
                //value
                userEntity.getId().toString(),
                1,
                TimeUnit.DAYS
        );
        Email email = new Email();
        email.setTitle("Blog网站用户注册激活邮件！");
        email.setToUser(userEntity.getEmail());
        email.setContent("<a href ='http://127.0.0.1:8001/user/activateUser/"
                + userEntity.getActivateCode() + "'>点击</a>这里激活"
        );

        ExecutorUtils.getExecutor().submit(() -> emailUtil.sendEmail(email));
    }

    /**
     * 激活账户接口
     * @param code
     * @return
     */
    @Override
    public Integer activateUser(String code) {
        String userId = stringRedisTemplate.opsForValue().get(String.format(UserConstants.ACTIVATEKEY, code));
        if (ObjectUtils.isEmpty(userId)) {
            throw new BlogException("激活码错误");
        }
        Integer integer = baseMapper.updateUserStatu(BlogStatus.USER_ACTIVATE.getCode(), userId, code);
        stringRedisTemplate.delete(String.format(UserConstants.ACTIVATEKEY, code));
        return integer;
    }

    /**
     * 登录接口
     * @param username
     * @param pwd
     * @return
     */
    @Override
    public UserToken login(String username, String pwd) {
        //根据用户名查寻用户
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        UserEntity userEntity = baseMapper.selectOne(queryWrapper);
        //判断用户是否存在
        if (userEntity == null) {
            throw new BlogException("用户不存在");
        }
        //判断密码是否正确
        if (!PasswordUtils.checkpw(pwd, userEntity.getPwd())) {
            throw new BlogException("密码错误");
        }
        //判断是否激活
        if (userEntity.getStatu() == BlogStatus.USER_UN_ACTIVATE.getCode()) {
            throw new BlogException("用户未激活");
        }
        //创建令牌
        UserToken token = new UserToken();
        token.setCreateTime(new Date());
        token.setUid(userEntity.getId());
        token.setUname(userEntity.getUsername());
        token.setHeadUrl(userEntity.getHeadUrl());
        token.setToken(UUIDUtils.getUUid());
        long nowTime = System.currentTimeMillis();
        long userDefaultTimeOut = UserConstants.USER_DEFAULE_TIMEOUT * 1000;
        long userTimeOut = nowTime + userDefaultTimeOut;
        Date ttl = new Date(userTimeOut);
        token.setTtl(ttl);
        String key = String.format(UserConstants.USER_LOGIN_TOKEN,token.getToken());
        redisTemplate.opsForValue().set(key,
                token,
                userTimeOut,
                TimeUnit.MILLISECONDS);
        return token;
    }

    /**
     * 退出接口
     * @param token
     */
    @Override
    public void logout(String token) {
        String key = String.format(UserConstants.USER_LOGIN_TOKEN,token);
        redisTemplate.delete(key);
    }

    /**
     * 修改密码接口
     * @param oldPassword
     * @param newPassword
     * @param newPassword2
     */
    @Override
    public void updatePassword(String oldPassword, String newPassword, String newPassword2) {
        UserToken userToken = userHelp.get();
        //判断值有效
        if (oldPassword==null || oldPassword.equals("")
            || newPassword == null || newPassword.equals("")
            || newPassword2 == null || newPassword2.equals("")){
            throw new BlogException("密码为空");
        }
        //判断两次密码是否相等
        if (!newPassword.equals(newPassword2)){
            throw new BlogException("两次密码不一致");
        }
        //判断原始密码是否正确
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id",userToken.getUid());
        UserEntity userEntity = baseMapper.selectOne(queryWrapper);
        if (userEntity == null){
            throw new BlogException("用户为空");
        }
        if (!PasswordUtils.checkpw(oldPassword,userEntity.getPwd())){
            throw new BlogException("密码校验失败");
        }
        //修改密码
        userEntity.setPwd(PasswordUtils.encode(newPassword));
        int i = baseMapper.updateById(userEntity);
        if (i<1){
            throw new BlogException("修改失败");
        }
        //退出登录
        redisTemplate.delete(String.format(UserConstants.USER_LOGIN_TOKEN,userToken.getToken()));

    }

    /**
     * 查询用户信息接口
     * @param uid
     * @return
     */
    @Override
    public UserEntity findUserById(Integer uid) {
        //先去redis查询用户信息
        String key = String.format(UserConstants.USER_INFO_KEY,uid);
        UserEntity userEntity = (UserEntity) redisTemplate.opsForValue().get(key);
        //如果没有用户信息
        if (userEntity == null){
            //加锁
            Lock lock = new ReentrantLock();
            try {
                boolean tryLock = lock.tryLock();
                if (tryLock){
                    userEntity = getById(uid);
                    if (userEntity == null){
                        redisTemplate.opsForValue().set(key,null,1,TimeUnit.DAYS);
                    }else {
                        redisTemplate.opsForValue().set(key,userEntity,1,TimeUnit.DAYS);
                    }
                }else {
                    //如果没有拿到锁，等待0.5s继续执行
                    Thread.sleep(500);
                    findUserById(uid);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //释放锁
                lock.unlock();
            }
        }
        //去mysql中查询并存储在redis中

        return userEntity;
    }

    @Override
    public void updateHeadUrl(Integer id, String url) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id",id);
        updateWrapper.set("head_url",url);
        baseMapper.update(null,updateWrapper);

    }
}
