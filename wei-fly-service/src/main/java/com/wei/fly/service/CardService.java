package com.wei.fly.service;

import com.wei.fly.interfaces.request.card.BindCardRequest;
import com.wei.fly.interfaces.request.card.CreateCardRequest;
import com.wei.fly.interfaces.request.card.ListCardRequest;
import com.wei.fly.interfaces.request.card.ModifyCardRequest;
import com.wei.fly.interfaces.request.card.RechargeRequest;
import com.wei.fly.interfaces.request.card.UnbindCardRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.card.CardResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
public interface CardService {
    Result<Page<CardResponse>> listCard(ListCardRequest request, UserSessionResponse sessionUser);

    Result createCard(BindCardRequest request);

    Result getCardByUserId(String userId);

    Result unbindCard(UnbindCardRequest request);

    Result activeCard(UnbindCardRequest request);

    Result recharge(RechargeRequest request);
}
