package com.wei.fly.service.impl;

import com.wei.fly.dao.CardMapper;
import com.wei.fly.dao.entity.Card;
import com.wei.fly.domain.UniqueDomain;
import com.wei.fly.interfaces.enums.BizTypeEnum;
import com.wei.fly.interfaces.enums.CardTypeEnum;
import com.wei.fly.interfaces.enums.ReturnStatusEnum;
import com.wei.fly.interfaces.enums.RoleTypeEnum;
import com.wei.fly.interfaces.request.card.BindCardRequest;
import com.wei.fly.interfaces.request.card.ListCardRequest;
import com.wei.fly.interfaces.request.card.UnbindCardRequest;
import com.wei.fly.interfaces.response.Page;
import com.wei.fly.interfaces.response.Result;
import com.wei.fly.interfaces.response.card.CardResponse;
import com.wei.fly.interfaces.response.user.UserSessionResponse;
import com.wei.fly.model.constant.CommonConstant;
import com.wei.fly.service.CardService;
import com.wei.fly.util.BeanUtils;
import com.wei.fly.util.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Slf4j
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private UniqueDomain uniqueDomain;

    @Override
    public Result<Page<CardResponse>> listCard(ListCardRequest request, UserSessionResponse sessionUser) {
        if (RoleTypeEnum.ADMIN != sessionUser.getRoleType()) {
            return new Result(ReturnStatusEnum.PERMISSION_INVALID.getValue(),
                    ReturnStatusEnum.PERMISSION_INVALID.getName());
        }

        int count = cardMapper.countCard(request);
        if (count == 0) {
            return Result.emptyPageResult();
        }
        final List<Card> cards = cardMapper.listCard(PageUtils.calculatePage(request, count));

        List<CardResponse> orderResponses = new ArrayList<>();
        for (Card card : cards) {
            CardResponse res = BeanUtils.transform(CardResponse.class, card, true);
            res.setCardType(CardTypeEnum.getType(card.getCardType()));
            orderResponses.add(res);
        }

        return new Result(new Page(count, orderResponses));
    }

    @Override
    public Result createCard(BindCardRequest request) {
        final Card card = BeanUtils.transform(Card.class, request);
        final Card tmpCard = cardMapper.selectByUserId(request.getUserId());
        if (tmpCard != null) {
            return new Result(ReturnStatusEnum.HAS_BIND_CARD.getValue(),
                    ReturnStatusEnum.HAS_BIND_CARD.getName());
        }

        card.setCardType(request.getCardType().getIndex());
        card.setCreateTime(new Date());
        card.setUpdateTime(new Date());
        card.setActive(false);
        card.setCanUseNum(0);
        card.setCardCode(uniqueDomain.getUniqueCode(CommonConstant.CARD_CODE_PREFIX,
                BizTypeEnum.CARD));
        cardMapper.insert(card);
        return new Result();
    }

    @Override
    public Result getCardByUserId(String userId) {
        final Card card = cardMapper.selectByUserId(userId);
        if (card == null) {
            return new Result(ReturnStatusEnum.CARD_NOT_EXISTS.getValue(),
                    ReturnStatusEnum.CARD_NOT_EXISTS.getName());
        }
        final CardResponse res = BeanUtils.transform(CardResponse.class, card, true);
        res.setCardType(CardTypeEnum.getType(card.getCardType()));
        return new Result(res);
    }

    @Override
    public Result unbindCard(UnbindCardRequest request) {
        cardMapper.deleteByCardCode(request.getCardCode());
        return new Result();
    }
}
