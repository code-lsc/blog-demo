package com.qf.blog.Service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.blog.Service.MessageService;
import com.qf.blog.dao.MessageDao;
import com.qf.blog.entity.MessageEntity;
import com.qf.blog.vo.MessageVo;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements
        MessageService {

    /*查询某个用户的所有消息*/
    @Override
    public Page<MessageVo> selectMessageByUserId(Page<MessageVo> page, Integer id) {
        Page<MessageVo> messageVoPage = baseMapper.selectMessageByUserId(page, id);
        return messageVoPage;
    }

    @Override
    public Integer selectNoReadCountById(Integer id) {
        return baseMapper.selectNoReadCountById(id);
    }


}
