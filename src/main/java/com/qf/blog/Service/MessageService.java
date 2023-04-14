package com.qf.blog.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qf.blog.entity.MessageEntity;
import com.qf.blog.vo.MessageVo;



public interface MessageService extends IService<MessageEntity> {

    Page<MessageVo> selectMessageByUserId(Page<MessageVo> page, Integer id);

    Integer selectNoReadCountById(Integer id);
}
