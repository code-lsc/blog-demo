package com.qf.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.blog.entity.MessageEntity;
import com.qf.blog.vo.MessageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MessageDao extends BaseMapper<MessageEntity> {

    Page<MessageVo> selectMessageByUserId(Page<MessageVo> page,@Param("id") Integer id);

    Integer selectNoReadCountById(@Param("id") Integer id);
}
