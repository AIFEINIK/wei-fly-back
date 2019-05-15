package com.wei.fly.web.controller;

import com.wei.fly.interfaces.CardFacade;
import com.wei.fly.interfaces.request.card.BindCardRequest;
import com.wei.fly.interfaces.request.card.ListCardRequest;
import com.wei.fly.interfaces.request.card.RechargeRequest;
import com.wei.fly.interfaces.request.card.UnbindCardRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.card.CardResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.service.CardService;
import com.wei.fly.util.DateUtils;
import io.swagger.annotations.Api;
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
@Api(value = "CardController", description = "CardController")
public class CardController implements CardFacade {

    @Autowired
    private CardService cardService;

    @Override
    public Result<Page<CardResponse>> listCard(ListCardRequest request, HttpServletRequest servletRequest) {
        if (StringUtils.isNotEmpty(request.getEndTime())) {
            request.setEndTime(DateUtils.getEndTime(request.getEndTime()));
        }
        final UserSessionResponse sessionUser = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        return cardService.listCard(request, sessionUser);
    }

    @Override
    public Result createCard(@RequestBody BindCardRequest request, HttpServletRequest servletRequest) {
        final UserSessionResponse user = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        request.setUserId(user.getUserId());
        return cardService.createCard(request);
    }

    @Override
    public Result getCard(HttpServletRequest servletRequest) {
        final UserSessionResponse user = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        return cardService.getCardByUserId(user.getUserId());
    }

    @Override
    public Result unbindCard(@RequestBody UnbindCardRequest request, HttpServletRequest servletRequest) {
        final UserSessionResponse user = (UserSessionResponse) servletRequest.getSession().getAttribute("user");
        request.setUserId(user.getUserId());
        return cardService.unbindCard(request);
    }

    @Override
    public Result activeCard(@RequestBody UnbindCardRequest request) {
        return cardService.activeCard(request);
    }

    @Override
    public Result recharge(@RequestBody RechargeRequest request) {
        return cardService.recharge(request);
    }
}
