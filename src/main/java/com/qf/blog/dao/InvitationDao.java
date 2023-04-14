package com.qf.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.blog.entity.InvitationEntity;
import com.qf.blog.vo.IndexDataVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvitationDao extends BaseMapper<InvitationEntity> {

    Page<IndexDataVo> selectIndexData(Page<IndexDataVo> page);

    Page<IndexDataVo> selectInvitationByUserId(Page<IndexDataVo> page, @Param("uid") Integer uid);

    Page<IndexDataVo> selectInvitationByInput(Page<IndexDataVo> page, @Param("keys")List key);
}
