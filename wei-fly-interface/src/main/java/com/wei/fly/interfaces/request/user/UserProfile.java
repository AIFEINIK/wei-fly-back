package com.wei.fly.interfaces.request.user;

import com.wei.fly.interfaces.request.BaseRequest;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/15
 * @Version 1.0.0
 */
@Data
public class UserProfile extends BaseRequest {

    private String token;
    private UserSessionResponse user;
}
