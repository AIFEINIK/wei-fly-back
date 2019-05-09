package com.wei.fly.interfaces.request.user;

import com.wei.fly.interfaces.request.BaseRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/28
 * @Version 1.0.0
 */
@Data
public class UserRegistryRequest extends BaseRequest {

    /** 用户唯一标识 */
    private String openId;

    /** 会话密钥 */
    private String sessionKey;
}
