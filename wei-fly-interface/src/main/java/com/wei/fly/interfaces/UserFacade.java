package com.wei.fly.interfaces;

import com.wei.fly.interfaces.request.user.CodeRequest;
import com.wei.fly.interfaces.request.user.ListUserRequest;
import com.wei.fly.interfaces.request.user.UserLoginRequest;
import com.wei.fly.interfaces.request.user.UserRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.user.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Feinik
 * @Discription 用户管理
 * @Data 2019/4/15
 * @Version 1.0.0
 */
@RequestMapping("/user")
public interface UserFacade {

    @PostMapping("backLogin")
    Result backLogin(@RequestBody UserLoginRequest userLogin,
                 HttpServletRequest request,
                 HttpServletResponse response);

    @PostMapping("logOut")
    Result logOut(HttpServletRequest request,
                  HttpServletResponse response);

    @PostMapping("frontLogin")
    Result frontLogin(CodeRequest request, HttpServletRequest servletRequest);

    @GetMapping("listUser")
    Result<Page<UserResponse>> listUser(ListUserRequest request,
                                        HttpServletRequest servletRequest);

    @PostMapping("editUser")
    Result editUser(UserRequest request, HttpServletRequest servletRequest);

    @PostMapping("delData")
    Result delData(HttpServletRequest servletRequest);
}
