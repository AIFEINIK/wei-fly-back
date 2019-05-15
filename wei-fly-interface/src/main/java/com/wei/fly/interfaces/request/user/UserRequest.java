package com.wei.fly.interfaces.request.user;

import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/9
 * @Version 1.0.0
 */
@Data
public class UserRequest {

    /** 用户昵称 */
    private String nickName;

    /** 性别 */
    private Integer gender;

    private String userId;
}
