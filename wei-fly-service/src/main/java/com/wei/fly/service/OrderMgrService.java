package com.wei.fly.service;

import com.wei.fly.interfaces.request.order.ListOrderRequest;
import com.wei.fly.interfaces.request.order.OrderRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.order.OrderResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse; /**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
public interface OrderMgrService {

    Result<Page<OrderResponse>> listOrder(ListOrderRequest request, UserSessionResponse sessionUser);

    Result createOrder(OrderRequest request);

    Result todayOrder(String userId);
}
