package com.wei.fly.service;

import com.wei.fly.interfaces.request.seat.CreateSeatRequest;
import com.wei.fly.interfaces.request.seat.ListSeatRequest;
import com.wei.fly.interfaces.request.seat.ModifySeatRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.seat.SeatResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse; /**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
public interface SeatService {

    Result<Page<SeatResponse>> listSeat(ListSeatRequest request, UserSessionResponse sessionUser);

    Result createSeat(CreateSeatRequest request);

    Result modifySeat(ModifySeatRequest request);

    Result seatScheduler(ModifySeatRequest request);

    Result clearScheduler(ModifySeatRequest request);
}
