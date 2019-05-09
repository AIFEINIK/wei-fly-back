package com.wei.fly.web.controller;

import com.wei.fly.interfaces.OrderMgrFacade;
import com.wei.fly.interfaces.request.order.ListOrderRequest;
import com.wei.fly.interfaces.request.order.OrderRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.order.OrderResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.service.OrderMgrService;
import com.wei.fly.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Slf4j
@RestController
@Api(value = "OrderMgrController", description = "订单管理")
public class OrderMgrController implements OrderMgrFacade {

    @Autowired
    private OrderMgrService orderMgrService;

    @Override
    @ApiOperation(value = "预约订单列表")
    public Result<Page<OrderResponse>> listOrder(ListOrderRequest request, HttpServletRequest servletRequest) {
        if (StringUtils.isNotEmpty(request.getEndTime())) {
            request.setEndTime(DateUtils.getEndTime(request.getEndTime()));
        }

        final UserSessionResponse sessionUser = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        return orderMgrService.listOrder(request, sessionUser);
    }

    @Override
    @ApiOperation(value = "创建预约单")
    public Result createOrder(@RequestBody OrderRequest request, HttpServletRequest servletRequest) {
        UserSessionResponse sessionUser = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        request.setUserId(sessionUser.getUserId());
        return orderMgrService.createOrder(request);
    }

    @Override
    public Result todayOrder(HttpServletRequest servletRequest) {
        UserSessionResponse sessionUser = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        return orderMgrService.todayOrder(sessionUser.getUserId());
    }
}
