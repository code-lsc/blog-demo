package com.qf.blog.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.entity.CommentEntity;
import com.qf.blog.vo.CommentVo;
import com.qf.blog.vo.ReplyVo;
import org.apache.ibatis.annotations.Param;

public interface CommentService extends IService<CommentEntity> {

    BlogPage<CommentVo> selectCommentListByTid(BlogPage<CommentVo> page, Integer id);

    Long getCommentCountByTopicId(Integer id, Integer type);

    Page<ReplyVo> selectReplyById(Page<ReplyVo> page, @Param("id") Integer id);
}
