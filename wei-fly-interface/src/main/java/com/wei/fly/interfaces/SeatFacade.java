package com.wei.fly.interfaces;

import com.wei.fly.interfaces.request.seat.CreateSeatRequest;
import com.wei.fly.interfaces.request.seat.ListSeatRequest;
import com.wei.fly.interfaces.request.seat.ModifySeatRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.seat.SeatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@RequestMapping("/seat")
public interface SeatFacade {

    @GetMapping("listSeat")
    Result<Page<SeatResponse>> listSeat(ListSeatRequest request, HttpServletRequest servletRequest);

    @PostMapping("createSeat")
    Result createSeat(CreateSeatRequest request);

    @PostMapping("seatScheduler")
    Result seatScheduler(ModifySeatRequest request);

    @PostMapping("modifySeat")
    Result modifySeat(ModifySeatRequest request);

    @PostMapping("clearScheduler")
    Result clearScheduler(ModifySeatRequest request);
}
