package com.wei.fly.interfaces.response.user;

import com.wei.fly.interfaces.enums.RoleTypeEnum;
import com.wei.fly.interfaces.enums.UserSexEnum;
import com.wei.fly.interfaces.enums.UserStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/15
 * @Version 1.0.0
 */
@Data
public class UserResponse implements Serializable {

    private String userId;

    private String token;

    /** 用户名称 */
    private String userName;

    /** 性别(1:男，2:女) */
    private UserSexEnum sex;

    /** 手机号 */
    private String phone;

    /** 注册时间 */
    private String createTime;

    /** 更新时间 */
    private String updateTime;

    /** 用户状态(0:正常， -1:已删除) */
    private UserStatusEnum userStatus;

    private RoleTypeEnum roleType;

    private String openId;
}
