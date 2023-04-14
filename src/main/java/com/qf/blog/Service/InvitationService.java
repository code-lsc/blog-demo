package com.qf.blog.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.entity.InvitationEntity;
import com.qf.blog.vo.IndexDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvitationService extends IService<InvitationEntity> {

    Page<IndexDataVo> indexPage(BlogPage<IndexDataVo> page);

    Page<IndexDataVo> selectInvitationByUserId(Page<IndexDataVo> blogPage, @Param("uid") Integer uid);

    Page<IndexDataVo> selectInvitationByInput(Page<IndexDataVo> page,  List key);
}
