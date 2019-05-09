package com.wei.fly.interfaces;

import com.wei.fly.interfaces.request.order.ListOrderRequest;
import com.wei.fly.interfaces.request.order.OrderRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.order.OrderResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Feinik
 * @Discription 订单管理
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@RequestMapping("/orderMgr")
public interface OrderMgrFacade {

    @GetMapping("listOrder")
    Result<Page<OrderResponse>> listOrder(ListOrderRequest request, HttpServletRequest servletRequest);

    @PostMapping("createOrder")
    Result createOrder(OrderRequest request, HttpServletRequest servletRequest);

    @GetMapping("todayOrder")
    Result todayOrder(HttpServletRequest servletRequest);

}
