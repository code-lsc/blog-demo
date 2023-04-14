package com.qf.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.blog.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author Java2108
 * @email java2108@qf.com
 * @date 2021-12-09 15:47:47
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    Integer updateUserStatu(@Param("statu") Integer statu
                        , @Param("uid") String uid
                        , @Param("code") String code);

}
