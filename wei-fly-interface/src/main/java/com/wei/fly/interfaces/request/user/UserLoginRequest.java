package com.wei.fly.interfaces.request.user;

import com.wei.fly.interfaces.annotation.RequiredParam;
import com.wei.fly.interfaces.request.BaseRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/15
 * @Version 1.0.0
 */
@Data
public class UserLoginRequest extends BaseRequest {

    @RequiredParam
    private String phone;

    @RequiredParam
    private String passwd;
}
