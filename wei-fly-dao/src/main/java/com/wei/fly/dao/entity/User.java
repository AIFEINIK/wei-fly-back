package com.wei.fly.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/15
 * @Version 1.0.0
 */
@Data
public class User {

    private String userId;

    /** 用户名称 */
    private String userName;

    /** 性别(1:男，2:女) */
    private Integer sex;

    /** 手机号 */
    private String phone;

    private String passwd;

    /** open_id */
    private String openId;

    /** session_key */
    private String sessionKey;

    /** 注册时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 用户状态(0:正常， -1:已删除) */
    private Integer userStatus;
    
    /** RoleTypeEnum */
    private Integer roleType;
}
