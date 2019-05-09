package com.wei.fly.domain;

import com.wei.fly.dao.OrderMgrMapper;
import com.wei.fly.dao.SeatMapper;
import com.wei.fly.dao.entity.Order;
import com.wei.fly.dao.entity.Seat;
import com.wei.fly.interfaces.enums.SeatStatusEnum;
import com.wei.fly.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/2
 * @Version 1.0.0
 */
@Component
@Slf4j
public class OrderDomain {

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private OrderMgrMapper orderMgrMapper;

    public boolean canOrder(Order order) {
        final Seat seat = seatMapper.selectById(order.getSeatId());
        return !(seat != null && SeatStatusEnum.getType(seat.getLocked()) == SeatStatusEnum.YES)
                || DateUtils.isForbidOrderTime();
    }

    public Order getTodayOrder(String userId) {
        //一个会员用户一天只能预约一次
        LocalTime nowTime = LocalTime.now();
        LocalDate useDate = LocalDate.now();
        if (nowTime.isAfter(DateUtils.orderMinTime)
                && nowTime.isBefore(DateUtils.orderMaxTime)) {
            useDate = LocalDate.now().plusDays(1);
        }
        final LocalDateTime beginDateTime = LocalDateTime.of(useDate, LocalTime.parse("09:00:00"));
        final LocalDateTime endDateTime = LocalDateTime.of(useDate, LocalTime.parse("21:00:00"));
        Date beginTime = Date.from(beginDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endTime = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return orderMgrMapper.isOrderExists(userId, beginTime, endTime);
    }
}
