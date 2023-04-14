package com.qf.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.entity.CommentEntity;
import com.qf.blog.vo.CommentVo;
import com.qf.blog.vo.MessageVo;
import com.qf.blog.vo.ReplyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {

    /**
     * 返回评论列表
     * @param page
     * @param id
     * @param type
     * @return
     */
    BlogPage<CommentVo> selectCommentListTid(@Param("page") BlogPage<CommentVo> page,
                                             @Param("tid") Integer id,
                                             @Param("type") Integer type);

    /**
     * 返回评论数量
     * @param id
     * @param type
     * @return
     */
    Long getCommentCountByTopicId(@Param("id") Integer id,@Param("type") Integer type);

    Page<ReplyVo> selectReplyById(Page<ReplyVo> page, @Param("id") Integer id);
}


