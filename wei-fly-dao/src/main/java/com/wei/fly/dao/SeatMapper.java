package com.wei.fly.dao;

import com.wei.fly.dao.entity.Seat;
import com.wei.fly.interfaces.request.seat.ListSeatRequest;
import com.wei.fly.interfaces.response.Result;

import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
public interface SeatMapper {
    int countSeat(ListSeatRequest request);

    List<Seat> listSeat(ListSeatRequest request);

    int insert(Seat seat);

    int update(Seat seat);

    Seat selectById(Integer id);


}
