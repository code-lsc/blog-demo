package com.qf.blog.Service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.blog.Service.CommentService;
import com.qf.blog.Service.ILikeService;
import com.qf.blog.Service.InvitationService;
import com.qf.blog.common.dto.BlogPage;
import com.qf.blog.common.status.BlogStatus;
import com.qf.blog.dao.InvitationDao;
import com.qf.blog.entity.InvitationDataEntity;
import com.qf.blog.entity.InvitationEntity;
import com.qf.blog.vo.IndexDataVo;
import com.qf.blog.vo.LikeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationServiceImpl extends ServiceImpl<InvitationDao, InvitationEntity>
        implements InvitationService {

    @Autowired
    private ILikeService likeService;

    @Autowired
    private CommentService commentService;

    @Override
    public Page<IndexDataVo> indexPage(BlogPage<IndexDataVo> blogPage) {
        Page<IndexDataVo> page = baseMapper.selectIndexData(blogPage);
        for (IndexDataVo indexDataVo:page.getRecords()) {
            LikeVo likeVo = new LikeVo();
            likeVo.setEntityType(1);
            likeVo.setEntityId((indexDataVo.getInvitationEntity().getId()));
            if (indexDataVo.getInvitationDataEntity() == null){
                indexDataVo.setInvitationDataEntity(new InvitationDataEntity());
            }
            Long likeCount = likeService.likeCount(likeVo);
            indexDataVo.getInvitationDataEntity().setLikes(Integer.parseInt(likeCount.toString()));

            Long commentCount = commentService.getCommentCountByTopicId(
                    indexDataVo.getInvitationEntity().getId(),
                    BlogStatus.TOPIC_COMMENT.getCode()
            );
            indexDataVo.getInvitationDataEntity().setComments(Integer.valueOf(commentCount.toString()));
        }
        return page;
    }

    @Override
    public Page<IndexDataVo> selectInvitationByUserId(Page<IndexDataVo> blogPage, Integer uid) {
        Page<IndexDataVo> page = baseMapper.selectInvitationByUserId(blogPage,uid);
        for (IndexDataVo indexDataVo:page.getRecords()) {
            LikeVo likeVo = new LikeVo();
            likeVo.setEntityType(1);
            likeVo.setEntityId((indexDataVo.getInvitationEntity().getId()));
            if (indexDataVo.getInvitationDataEntity() == null){
                indexDataVo.setInvitationDataEntity(new InvitationDataEntity());
            }
            Long likeCount = likeService.likeCount(likeVo);
            indexDataVo.getInvitationDataEntity().setLikes(Integer.parseInt(likeCount.toString()));

            Long commentCount = commentService.getCommentCountByTopicId(
                    indexDataVo.getInvitationEntity().getId(),
                    BlogStatus.TOPIC_COMMENT.getCode()
            );
            indexDataVo.getInvitationDataEntity().setComments(Integer.valueOf(commentCount.toString()));
        }
        return page;
    }

    @Override
    public Page<IndexDataVo> selectInvitationByInput(Page<IndexDataVo> blogPage, List key) {
        Page<IndexDataVo> page = baseMapper.selectInvitationByInput(blogPage,key);
        for (IndexDataVo indexDataVo:page.getRecords()) {
            LikeVo likeVo = new LikeVo();
            likeVo.setEntityType(1);
            likeVo.setEntityId((indexDataVo.getInvitationEntity().getId()));
            if (indexDataVo.getInvitationDataEntity() == null){
                indexDataVo.setInvitationDataEntity(new InvitationDataEntity());
            }
            Long likeCount = likeService.likeCount(likeVo);
            indexDataVo.getInvitationDataEntity().setLikes(Integer.parseInt(likeCount.toString()));

            Long commentCount = commentService.getCommentCountByTopicId(
                    indexDataVo.getInvitationEntity().getId(),
                    BlogStatus.TOPIC_COMMENT.getCode()
            );
            indexDataVo.getInvitationDataEntity().setComments(Integer.valueOf(commentCount.toString()));
        }
        return page;
    }


}
