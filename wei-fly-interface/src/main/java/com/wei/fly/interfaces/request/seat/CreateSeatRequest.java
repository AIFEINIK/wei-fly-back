package com.wei.fly.interfaces.request.seat;

import com.wei.fly.interfaces.annotation.RequiredParam;
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
public class CreateSeatRequest extends BaseRequest {

    /** 座位号 */
    @RequiredParam
    private String num;

    /** 小标题 */
    private String title;

    /** 座位类型（1:单座，2:双座） */
    @RequiredParam
    private SeatTypeEnum seatType;
}
