package com.qf.blog.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qf.blog.entity.UserEntity;
import com.qf.blog.vo.UserToken;

public interface UserService extends IService<UserEntity> {

    void register(UserEntity userEntity);

    Integer activateUser(String code);

    UserToken login(String username, String pwd);

    void logout(String token);

    void updatePassword(String oldPassword, String newPassword, String newPassword2);

    UserEntity findUserById(Integer uid);

    void updateHeadUrl(Integer id,String url);
}
