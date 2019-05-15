package com.wei.fly.dao.entity;

import com.wei.fly.interfaces.enums.CardStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Data
public class Card {

    /** 主键 */
    private Integer id;

    private String userId;

    /** 会员卡名称 */
    private String cardName;

    /** 会员卡编号 */
    private String cardCode;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 卡类型(1:周卡，2:月卡，3:季度卡，4:年卡) */
    private Integer cardType;
    
    /** 剩余可使用次数 */
    private Integer canUseNum;

    /** 是否激活 */
    private Boolean active;

    private Integer cardStatus;
}
