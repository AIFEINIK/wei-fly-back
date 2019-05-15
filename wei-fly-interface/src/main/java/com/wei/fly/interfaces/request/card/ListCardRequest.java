package com.wei.fly.interfaces.request.card;

import com.wei.fly.interfaces.enums.CardStatusEnum;
import com.wei.fly.interfaces.enums.CardTypeEnum;
import com.wei.fly.interfaces.request.PageRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Data
public class ListCardRequest extends PageRequest {

    private String userId;

    /** 会员卡编号 */
    private String cardCode;

    /** 卡类型(1:周卡，2:月卡，3:季度卡，4:年卡) */
    private CardTypeEnum cardType;

    private CardStatusEnum cardStatus;

    /** 是否激活 */
    private Boolean active;
}
