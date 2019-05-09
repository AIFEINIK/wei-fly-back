package com.wei.fly.web.controller;

import com.wei.fly.interfaces.SeatFacade;
import com.wei.fly.interfaces.request.seat.CreateSeatRequest;
import com.wei.fly.interfaces.request.seat.ListSeatRequest;
import com.wei.fly.interfaces.request.seat.ModifySeatRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.seat.SeatResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.service.SeatService;
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
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Slf4j
@RestController
@Api(value = "SeatController", description = "SeatController")
public class SeatController implements SeatFacade {

    @Autowired
    private SeatService seatService;

    @Override
    @ApiOperation(value = "")
    public Result<Page<SeatResponse>> listSeat(ListSeatRequest request, HttpServletRequest servletRequest) {
        if (StringUtils.isNotEmpty(request.getEndTime())) {
            request.setEndTime(DateUtils.getEndTime(request.getEndTime()));
        }

        final UserSessionResponse sessionUser = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        return seatService.listSeat(request, sessionUser);
    }

    @Override
    @ApiOperation(value = "")
    public Result createSeat(@RequestBody CreateSeatRequest request) {
        return seatService.createSeat(request);
    }

    @Override
    public Result seatScheduler(@RequestBody ModifySeatRequest request) {
        return seatService.seatScheduler(request);
    }

    @Override
    @ApiOperation(value = "")
    public Result modifySeat(@RequestBody ModifySeatRequest request) {
        return seatService.modifySeat(request);
    }

    @Override
    @ApiOperation(value = "")
    public Result clearScheduler(@RequestBody ModifySeatRequest request) {
        return seatService.clearScheduler(request);
    }
}
