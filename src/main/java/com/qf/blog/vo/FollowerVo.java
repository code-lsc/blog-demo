package com.qf.blog.vo;

import lombok.Data;

import java.util.Date;

/**
 * 关注信息Vo
 */
@Data
public class FollowerVo {

    private Integer userId;//关注人或粉丝的id
    private String userName;//用户名
    private Date createTime;//关注时间
    private String headUrl;//头像
}
