package com.qf.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserToken implements Serializable {

    private Integer uid;//用户id
    private String uname;//用户名
    private Date ttl;//过期时间
    private Date createTime;//登录时间
    private String headUrl;//头像
    private String token;//令牌
}
