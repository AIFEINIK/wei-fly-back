package com.wei.fly.interfaces.response.card;

import com.wei.fly.interfaces.enums.CardStatusEnum;
import com.wei.fly.interfaces.enums.CardTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Data
public class CardResponse implements Serializable {

    private String userId;

    /** 会员卡名称 */
    private String cardName;

    /** 会员卡编号 */
    private String cardCode;

    /** 创建时间 */
    private String createTime;

    /** 更新时间 */
    private String updateTime;

    /** 卡类型(1:周卡，2:月卡，3:季度卡，4:年卡) */
    private CardTypeEnum cardType;

    /** 剩余可使用次数 */
    private Integer canUseNum;

    /** 是否激活 */
    private Boolean active;

    private CardStatusEnum cardStatus;
}
