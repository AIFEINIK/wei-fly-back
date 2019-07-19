package com.wei.fly.service.impl;

import com.google.common.collect.Lists;
import com.wei.fly.dao.SeatMapper;
import com.wei.fly.dao.entity.Seat;
import com.wei.fly.interfaces.enums.ReturnStatusEnum;
import com.wei.fly.interfaces.enums.SeatStatusEnum;
import com.wei.fly.interfaces.enums.SeatTypeEnum;
import com.wei.fly.interfaces.request.seat.CreateSeatRequest;
import com.wei.fly.interfaces.request.seat.ListSeatRequest;
import com.wei.fly.interfaces.request.seat.ModifySeatRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.seat.SeatResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.service.SeatService;
import com.wei.fly.util.BeanUtils;
import com.wei.fly.util.DateUtils;
import com.wei.fly.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatMapper seatMapper;

    @Override
    public Result<Page<SeatResponse>> listSeat(ListSeatRequest request, UserSessionResponse sessionUser) {
        int count = seatMapper.countSeat(request);
        if (count == 0) {
            return Result.emptyPageResult();
        }
        final List<Seat> seats = seatMapper.listSeat(PageUtils.calculatePage(request, count));
        //List<List<Seat>> result = new ArrayList<>();
        //final List<Seat> oneSeat = collect.get(SeatTypeEnum.ONE_SEAT.getIndex());
        //final List<Seat> twoSeat = collect.get(SeatTypeEnum.TWO_SEAT.getIndex());
        //final List<Seat> threeSeat = collect.get(SeatTypeEnum.THREE_SEAT.getIndex());
        //final List<Seat> fourSeat = collect.get(SeatTypeEnum.FOUR_SEAT.getIndex());
        //if (oneSeat != null) {
        //    final List<List<Object>> oneSeatPar = Lists.partition(new ArrayList<>(), 6);
        //}


        List<SeatResponse> seatResponses = new ArrayList<>();
        for (Seat seat : seats) {
            SeatResponse res = BeanUtils.transform(SeatResponse.class, seat, true);
            res.setLocked(SeatStatusEnum.getType(seat.getLocked()));
            res.setSeatType(SeatTypeEnum.getType(seat.getSeatType()));
            seatResponses.add(res);
        }
        //final List<SeatResponse> result = seatResponses.stream().sorted(Comparator.comparing(r -> r.getSeatType().getIndex())).collect(Collectors.toList());
        return new Result(new Page(count, seatResponses));
    }

    @Override
    public Result createSeat(CreateSeatRequest request) {
        final Seat seat = BeanUtils.transform(Seat.class, request);
        seat.setSeatType(request.getSeatType().getIndex());
        seat.setCreateTime(new Date());
        seat.setUpdateTime(new Date());
        seat.setLocked(SeatStatusEnum.NO.getIndex());
        seatMapper.insert(seat);
        return new Result();
    }

    @Override
    public Result modifySeat(ModifySeatRequest request) {
        final Seat seat = BeanUtils.transform(Seat.class, request);
        seat.setSeatType(request.getSeatType().getIndex());
        seat.setUpdateTime(new Date());
        seatMapper.update(seat);
        return new Result();
    }

    @Override
    public Result seatScheduler(ModifySeatRequest request) {
        final Seat dbSeat = seatMapper.selectById(request.getId());
        if (dbSeat != null && SeatStatusEnum.YES == SeatStatusEnum.getType(dbSeat.getLocked())) {
            return new Result(ReturnStatusEnum.SEAT_HAS_LOCKED.getValue(),
                    ReturnStatusEnum.SEAT_HAS_LOCKED.getName());
        }

        final Seat seat = BeanUtils.transform(Seat.class, request);
        seat.setUpdateTime(new Date());
        seat.setLockedEndTime(request.getLockedEndTime());
        seat.setLocked(SeatStatusEnum.YES.getIndex());
        seatMapper.update(seat);
        return new Result();
    }

    @Override
    public Result clearScheduler(ModifySeatRequest request) {
        final Seat dbSeat = seatMapper.selectById(request.getId());
        if (dbSeat != null
                && SeatStatusEnum.YES == SeatStatusEnum.getType(dbSeat.getLocked())
                && StringUtils.isEmpty(dbSeat.getLockedEndTime())) {
            return new Result(ReturnStatusEnum.SEAT_HAS_LOCKED_VIP.getValue(),
                    ReturnStatusEnum.SEAT_HAS_LOCKED_VIP.getName());
        }

        Seat seat = new Seat();
        seat.setId(request.getId());
        seat.setLocked(SeatStatusEnum.NO.getIndex());
        seat.setLockedEndTime(StringUtils.EMPTY);
        seatMapper.update(seat);
        return new Result();
    }
}
