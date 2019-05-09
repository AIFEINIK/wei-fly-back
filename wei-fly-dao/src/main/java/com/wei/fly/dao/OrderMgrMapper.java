package com.wei.fly.dao;

import com.wei.fly.dao.entity.Order;
import com.wei.fly.interfaces.request.order.ListOrderRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
public interface OrderMgrMapper {

    int countOrder(ListOrderRequest request);

    List<Order> listOrder(ListOrderRequest request);

    int insert(Order order);

    List<Order> selectByCondition(Order order);

    Order selectById(@Param(value = "orderCode") String orderCode);

    Order isOrderExists(@Param(value = "userId") String userId,
                        @Param(value = "beginTime") Date beginTime,
                        @Param(value = "endTime") Date endTime);
}
