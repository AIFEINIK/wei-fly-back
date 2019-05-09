package com.wei.fly.interfaces;

import com.wei.fly.interfaces.request.card.BindCardRequest;
import com.wei.fly.interfaces.request.card.ListCardRequest;
import com.wei.fly.interfaces.request.card.ModifyCardRequest;
import com.wei.fly.interfaces.request.card.CreateCardRequest;
import com.wei.fly.interfaces.request.card.UnbindCardRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.card.CardResponse;
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
@RequestMapping("/card")
public interface CardFacade {

    @GetMapping("listCard")
    Result<Page<CardResponse>> listCard(ListCardRequest request, HttpServletRequest servletRequest);

    @PostMapping("createCard")
    Result createCard(BindCardRequest request, HttpServletRequest servletRequest);

    @GetMapping("getCard")
    Result getCard(HttpServletRequest servletRequest);

    @PostMapping("unbindCard")
    Result unbindCard(UnbindCardRequest request);

}
