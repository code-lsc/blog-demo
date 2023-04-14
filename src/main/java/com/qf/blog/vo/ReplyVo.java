package com.qf.blog.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyVo {

    private Integer id;//主键id
    private Integer uid;//回复的用户id
    private String headUrl;//回复的用户头像
    private String userName;//回复的用户名
    private String content;//回复的内容
    private Date createTime;//回复时间
}
