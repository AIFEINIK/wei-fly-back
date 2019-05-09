package com.wei.fly.web.controller;

import com.alibaba.fastjson.JSON;
import com.wei.fly.interfaces.UserFacade;
import com.wei.fly.interfaces.enums.ReturnStatusEnum;
import com.wei.fly.interfaces.request.user.CodeRequest;
import com.wei.fly.interfaces.request.user.ListUserRequest;
import com.wei.fly.interfaces.request.user.UserLoginRequest;
import com.wei.fly.interfaces.request.user.UserProfile;
import com.wei.fly.interfaces.request.user.UserRegistryRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.user.UserResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.interfaces.response.wx.CodeResponse;
import com.wei.fly.model.constant.CommonConstant;
import com.wei.fly.service.UserService;
import com.wei.fly.util.BeanUtils;
import com.wei.fly.util.CacheUtils;
import com.wei.fly.util.DateUtils;
import com.wei.fly.util.UUID;
import com.wei.fly.util.httpclient.HttpClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Slf4j
@RestController
@Api(value = "UserController", description = "用户管理")
public class UserController implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpClient httpClient;

    @Override
    @ApiOperation(value = "后台登录")
    public Result backLogin(@RequestBody UserLoginRequest userLogin,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        final Result result = userService.backLogin(userLogin);
        if (ReturnStatusEnum.SUCCESS.getValue() == result.getStatus()) {
            UserSessionResponse user = (UserSessionResponse) result.getData();
            final UserResponse userResponse = setSession(request, user);
            result.setData(userResponse);
        }
        return result;
    }

    private UserResponse setSession(HttpServletRequest request, UserSessionResponse user) {
        UserProfile profile = new UserProfile();
        profile.setToken(UUID.get());
        profile.setUser(user);

        CacheUtils.set(profile.getToken(), profile);

        request.getSession().setAttribute("user", user);

        final UserResponse userResponse = BeanUtils.transform(UserResponse.class, user);
        userResponse.setToken(profile.getToken());
        return userResponse;
    }

    @Override
    public Result logOut(HttpServletRequest request, HttpServletResponse response) {
        try {
            CacheUtils.delete(CommonConstant.TOKEN_KEY);
            request.logout();
        } catch (ServletException e) {
            log.error("退出登录失败， cause is:{}", e);
        }
        return new Result();
    }

    @Override
    @ApiOperation(value = "前台登录")
    public Result frontLogin(@RequestBody CodeRequest request, HttpServletRequest servletRequest) {
        final String url = MessageFormat.format(CommonConstant.WX_CODE_SESSION_URL, request.getCode());
        try {
            final String resp = httpClient.get(url);
            log.info("小程序登录请求微信返回数据：" + resp);
            final CodeResponse codeResponse = JSON.parseObject(resp, CodeResponse.class);
            if (null == codeResponse.getErrcode()) {
                //登录成功
                UserRegistryRequest ur = new UserRegistryRequest();
                ur.setOpenId(codeResponse.getOpenid());
                ur.setSessionKey(codeResponse.getSession_key());
                Result result = userService.registryUser(ur);
                if (ReturnStatusEnum.SUCCESS.getValue() == result.getStatus()) {
                    UserSessionResponse user = (UserSessionResponse) result.getData();
                    final UserResponse userResponse = setSession(servletRequest, user);
                    result.setData(userResponse);
                }
                return result;

            } else {
                //登录失败
                return new Result(ReturnStatusEnum.WX_LOGIN_FAILED.getValue(),
                        ReturnStatusEnum.WX_LOGIN_FAILED.getName());
            }

        } catch (IOException e) {
            log.error("小程序登录失败，cause is:{}", e);
        }

        return null;
    }

    @Override
    @ApiOperation(value = "用户列表")
    public Result<Page<UserResponse>> listUser(ListUserRequest request, HttpServletRequest servletRequest) {
        if (StringUtils.isNotEmpty(request.getEndTime())) {
            request.setEndTime(DateUtils.getEndTime(request.getEndTime()));
        }

        final UserSessionResponse sessionUser = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        return userService.listUser(request, sessionUser);
    }
}
