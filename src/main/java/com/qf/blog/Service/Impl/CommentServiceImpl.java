package com.qf.blog.Service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.blog.Service.CommentService;
import com.qf.blog.Service.ILikeService;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.common.help.UserHelp;
import com.qf.blog.common.status.BlogStatus;
import com.qf.blog.dao.CommentDao;
import com.qf.blog.entity.CommentEntity;
import com.qf.blog.vo.CommentVo;
import com.qf.blog.vo.LikeVo;
import com.qf.blog.vo.ReplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity>
        implements CommentService {

    @Autowired
    private UserHelp userHelp;

    @Autowired
    private ILikeService likeService;

    @Override
    public BlogPage<CommentVo> selectCommentListByTid(BlogPage<CommentVo> page, Integer id) {

        //查询一级评论（对帖子的评论）
        page= baseMapper.selectCommentListTid(page,id, BlogStatus.TOPIC_COMMENT.getCode());

        BlogPage newReplyPage = new BlogPage();
        LikeVo likeVo = new LikeVo();
        likeVo.setEntityType(2);
        if (userHelp.get() != null){
            likeVo.setUserId(userHelp.get().getUid());
        }
        //查看二级评论（对评论的评论）
        List<CommentVo> records = page.getRecords();
        for (CommentVo comment : records) {
            //循环一级评论，查询二级评论
            likeVo.setEntityId(comment.getCommentEntity().getId());
            //设置该一级评论的点赞状态，判断该用户目前是否点赞,并查询评论的点赞数
            comment.setLikeStatus(likeService.likeStatus(likeVo)?1:0);
            comment.setLikeCount(likeService.likeCount(likeVo));
            //查询某一个一级评论的的所有二级评论
            Integer cid = comment.getCommentEntity().getId();
            Page<CommentVo> replyPage = baseMapper.selectCommentListTid(newReplyPage,cid,
                    BlogStatus.TOPIC_REPLY.getCode());

            List<CommentVo> records1 = replyPage.getRecords();
            //查询二级评论的点赞数量和点赞状态
            for (CommentVo commentVo : records1) {
                //设置二级评论id
                likeVo.setEntityId(commentVo.getCommentEntity().getId());
                //设置该二级评论的点赞状态，判断该用户目前是否点赞,并查询评论的点赞数
                commentVo.setLikeStatus(likeService.likeStatus(likeVo)?1:0);
                commentVo.setLikeCount(likeService.likeCount(likeVo));
            }

            comment.setReplyList(records1);

        }
        return page;
    }

    @Override
    public Long getCommentCountByTopicId(Integer id, Integer type) {
        return baseMapper.getCommentCountByTopicId(id, type);
    }

    @Override
    public Page<ReplyVo> selectReplyById(Page<ReplyVo> page, Integer id) {
        return baseMapper.selectReplyById(page,id);
    }
}
