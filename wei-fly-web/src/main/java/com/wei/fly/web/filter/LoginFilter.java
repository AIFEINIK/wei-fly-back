package com.wei.fly.web.filter;

import com.alibaba.fastjson.JSON;
import com.wei.fly.interfaces.enums.ReturnStatusEnum;
import com.wei.fly.interfaces.request.user.UserProfile;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.model.constant.CommonConstant;
import com.wei.fly.util.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/15
 * @Version 1.0.0
 */
@Slf4j
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        response.setContentType("application/json;charset=utf-8");

        final String uri = httpServletRequest.getRequestURI().toString();
        if (uri.contains("/user/backLogin")
                || uri.contains("/user/frontLogin")
                || uri.contains("swagger")
                || uri.contains("api-docs")) {
            chain.doFilter(request, response);
            //白名单直接放过
            return;
        }

        //获取token
        String token = getHeaderByName(httpServletRequest, CommonConstant.TOKEN_KEY);
        if (!StringUtils.hasText(token)) {
            log.info("token invalid");
            doFail(httpServletResponse);
            return;
        }

        try {
            UserProfile profile = (UserProfile) CacheUtils.get(token);
            if (profile == null || profile.getToken() == null || profile.getUser() == null) {
                log.info("find session by token failed.");
                doFail(httpServletResponse);
            } else {
                doSuccess(httpServletRequest, httpServletResponse, chain, profile);
            }
        } catch (Exception e) {
            log.error("从缓存获取token失败, cause is:{}", e);
            doFail(httpServletResponse);
        }
    }

    private void doSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, UserProfile profile) throws IOException, ServletException {
        final UserSessionResponse user = profile.getUser();
        request.getSession().setAttribute("user", user);
        //刷新缓存
        CacheUtils.set(profile.getToken(), profile);

        chain.doFilter(request, response);

    }

    private void doFail(HttpServletResponse response) throws IOException {
        response.getWriter().print(JSON.toJSONString(new Result(ReturnStatusEnum.INVALID_TOKEN.getValue(),
                ReturnStatusEnum.INVALID_TOKEN.getName())));
    }

    private String getHeaderByName(HttpServletRequest request, String name) {
        if (request == null) return null;
        final String header = request.getHeader(name);
        return header;
    }

    @Override
    public void destroy() {

    }
}
