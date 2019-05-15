package com.wei.fly.interfaces.request.user;

import com.wei.fly.interfaces.enums.RoleTypeEnum;
import com.wei.fly.interfaces.request.PageRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Data
public class ListUserRequest extends PageRequest {

    private String userId;
    private String phone;
    private String userName;
    private RoleTypeEnum roleType;
}
