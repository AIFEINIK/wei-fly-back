package com.wei.fly.service;

import com.wei.fly.interfaces.request.user.ListUserRequest;
import com.wei.fly.interfaces.request.user.UserLoginRequest;
import com.wei.fly.interfaces.request.user.UserRegistryRequest;
import com.wei.fly.interfaces.request.user.UserRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.user.UserResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
public interface UserService {

    Result backLogin(UserLoginRequest request);

    Result<Page<UserResponse>> listUser(ListUserRequest request, UserSessionResponse sessionUser);

    Result registryUser(UserRegistryRequest request);

    Result editUser(UserRequest request);

    Result delData(String userId);
}
