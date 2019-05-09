package com.wei.fly.dao;

import com.wei.fly.dao.entity.Card;
import com.wei.fly.dao.entity.UserCard;
import com.wei.fly.interfaces.request.card.ListCardRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
public interface CardMapper {

    int countCard(ListCardRequest request);

    List<Card> listCard(ListCardRequest request);

    int insert(Card card);

    int update(Card card);

    Card selectByUserId(@Param("userId") String userId);

    void deleteByCardCode(@Param("cardCode") String cardCode);
}
