package com.wei.fly.interfaces.request.seat;

import com.wei.fly.interfaces.enums.SeatTypeEnum;
import com.wei.fly.interfaces.request.BaseRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Data
public class ModifySeatRequest extends BaseRequest {

    private Integer id;

    /** 座位号 */
    private String num;

    /** 小标题 */
    private String title;

    /** 座位类型（1:单座，2:双座） */
    private SeatTypeEnum seatType;

    /** 座位锁定结束时间（倒计时使用） */
    private String lockedEndTime;
}
