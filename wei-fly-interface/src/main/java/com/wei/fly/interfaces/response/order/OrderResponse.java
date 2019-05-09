package com.wei.fly.interfaces.response.order;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Data
public class OrderResponse implements Serializable {

    /** 创建时间 */
    private String createTime;

    /** 预约流水号 */
    private String orderCode;

    /** 会员卡编号 */
    private String cardCode;

    /** 预约到店时间 */
    private String useTime;

    /** 用户编号 */
    private String userId;

    /** f_seat表ID */
    private Integer seatId;

    private String seatNum;
}
