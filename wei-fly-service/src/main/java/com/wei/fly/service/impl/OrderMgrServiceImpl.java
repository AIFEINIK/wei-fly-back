package com.wei.fly.service.impl;

import com.wei.fly.dao.CardMapper;
import com.wei.fly.dao.OrderMgrMapper;
import com.wei.fly.dao.RecordMapper;
import com.wei.fly.dao.SeatMapper;
import com.wei.fly.dao.UserMapper;
import com.wei.fly.dao.entity.Card;
import com.wei.fly.dao.entity.Order;
import com.wei.fly.dao.entity.Record;
import com.wei.fly.dao.entity.Seat;
import com.wei.fly.dao.entity.User;
import com.wei.fly.domain.OrderDomain;
import com.wei.fly.domain.UniqueDomain;
import com.wei.fly.interfaces.enums.BizTypeEnum;
import com.wei.fly.interfaces.enums.ReturnStatusEnum;
import com.wei.fly.interfaces.enums.RoleTypeEnum;
import com.wei.fly.interfaces.enums.SeatStatusEnum;
import com.wei.fly.interfaces.enums.SeatTypeEnum;
import com.wei.fly.interfaces.request.order.ListOrderRequest;
import com.wei.fly.interfaces.request.order.OrderRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.order.OrderResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.model.constant.CommonConstant;
import com.wei.fly.service.OrderMgrService;
import com.wei.fly.util.BeanUtils;
import com.wei.fly.util.DateUtils;
import com.wei.fly.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Service
@Slf4j
public class OrderMgrServiceImpl implements OrderMgrService {

    @Autowired
    private OrderMgrMapper orderMgrMapper;
    @Autowired
    private UniqueDomain uniqueDomain;
    @Autowired
    private OrderDomain orderDomain;
    @Autowired
    private SeatMapper seatMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Result<Page<OrderResponse>> listOrder(ListOrderRequest request, UserSessionResponse sessionUser) {
        if (RoleTypeEnum.ADMIN != sessionUser.getRoleType()) {
            return new Result(ReturnStatusEnum.PERMISSION_INVALID.getValue(),
                    ReturnStatusEnum.PERMISSION_INVALID.getName());
        }

        int count = orderMgrMapper.countOrder(request);
        if (count == 0) {
            return Result.emptyPageResult();
        }
        final List<Order> orders = orderMgrMapper.listOrder(PageUtils.calculatePage(request, count));
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            OrderResponse res = BeanUtils.transform(OrderResponse.class, order, true);
            orderResponses.add(res);
        }
        return new Result(new Page(count, orderResponses));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result createOrder(OrderRequest request) {
        //是否是会员用户
        final User user = userMapper.selectByUserId(request.getUserId());
        if (RoleTypeEnum.CUSTOMER.getIndex() == user.getRoleType()) {
            return new Result(ReturnStatusEnum.NOT_VIP_USER.getValue(),
                    ReturnStatusEnum.NOT_VIP_USER.getName());
        }

        final Card card = cardMapper.selectByUserId(request.getUserId());
        if (card == null) {
            return new Result(ReturnStatusEnum.CARD_NOT_EXISTS.getValue(),
                    ReturnStatusEnum.CARD_NOT_EXISTS.getName());

        } else if (!card.getActive()) {
            return new Result(ReturnStatusEnum.CARD_NOT_ACTIVE.getValue(),
                    ReturnStatusEnum.CARD_NOT_ACTIVE.getName());

        } else if (card.getCanUseNum() == 0) {
            //余额不足
            return new Result(ReturnStatusEnum.NOT_ENOUGH_BALANCE.getValue(),
                    ReturnStatusEnum.NOT_ENOUGH_BALANCE.getName());
        }

        final Order todayOrder = orderDomain.getTodayOrder(request.getUserId());
        if (todayOrder != null) {
            String useTime = DateUtils.parseDateToString(todayOrder.getUseTime(), DateUtils.DATE_FORMAT_MINUTE);
            return new Result(ReturnStatusEnum.ORDER_EXISTS.getValue(),
                    MessageFormat.format(ReturnStatusEnum.ORDER_EXISTS.getName(), useTime));
        }

        Seat seat = seatMapper.selectById(request.getSeatId());
        if (seat != null && SeatStatusEnum.getType(seat.getLocked()) == SeatStatusEnum.YES) {
            return new Result(ReturnStatusEnum.SEAT_HAS_LOCKED.getValue(),
                    ReturnStatusEnum.SEAT_HAS_LOCKED.getName());

        } else if (DateUtils.isForbidOrderTime()){
            return new Result(ReturnStatusEnum.FORBID_ORDER_TIME.getValue(),
                    ReturnStatusEnum.FORBID_ORDER_TIME.getName());
        }

        if (DateUtils.getUseDateTime(LocalTime.parse(request.getUseTime())).isBefore(LocalDateTime.now())) {
            return new Result(ReturnStatusEnum.INVALID_USE_TIME.getValue(),
                    ReturnStatusEnum.INVALID_USE_TIME.getName());
        }

        //锁定座位
        Seat updateSeat = new Seat();
        updateSeat.setLocked(SeatStatusEnum.YES.getIndex());
        updateSeat.setId(request.getSeatId());
        updateSeat.setUpdateTime(new Date());
        seatMapper.update(updateSeat);

        //扣减会员卡余额
        int consumeNum = 1;
        switch (SeatTypeEnum.getType(seat.getSeatType())) {
            case ONE_SEAT:
                consumeNum = 1;
                break;
            case TWO_SEAT:
                consumeNum = 2;
                break;
            case THREE_SEAT:
                consumeNum = 3;
                break;
            default:
                consumeNum = 1;
        }

        Card updateCard = new Card();
        updateCard.setCanUseNum(card.getCanUseNum() - consumeNum);
        updateCard.setUpdateTime(new Date());
        updateCard.setUserId(card.getUserId());
        updateCard.setCardCode(card.getCardCode());
        cardMapper.update(updateCard);

        //创建预约订单
        final Order order = BeanUtils.transform(Order.class, request);
        order.setCreateTime(new Date());
        order.setUseTime(DateUtils.handleUseTime(LocalTime.parse(request.getUseTime())));
        order.setOrderCode(uniqueDomain.getUniqueCode(CommonConstant.ORDER_CODE_PREFIX, BizTypeEnum.ORDER));
        order.setCardCode(card.getCardCode());
        orderMgrMapper.insert(order);

        //创建消费记录
        Record record = new Record();
        record.setUserId(request.getUserId());
        record.setCardCode(card.getCardCode());
        record.setConsumeNum(consumeNum);
        record.setCreateTime(new Date());
        record.setSeatNum(seat.getNum());
        record.setSeatType(seat.getSeatType());
        record.setOrderCode(order.getOrderCode());
        recordMapper.insert(record);

        final OrderResponse res = BeanUtils.transform(OrderResponse.class, order, true);
        return new Result(res);
    }

    @Override
    public Result todayOrder(String userId) {
        final Order todayOrder = orderDomain.getTodayOrder(userId);
        if (todayOrder == null) {
            return new Result(ReturnStatusEnum.NO_ORDER.getValue(),
                    ReturnStatusEnum.NO_ORDER.getName());
        }
        final OrderResponse res = BeanUtils.transform(OrderResponse.class, todayOrder, true);
        return new Result(res);
    }
}
