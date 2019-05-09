package com.wei.fly.interfaces.request.user;

import com.wei.fly.interfaces.annotation.RequiredParam;
import com.wei.fly.interfaces.request.BaseRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/27
 * @Version 1.0.0
 */
@Data
public class CodeRequest extends BaseRequest {

    /** 微信前端登录返回的code */
    @RequiredParam
    private String code;
}
