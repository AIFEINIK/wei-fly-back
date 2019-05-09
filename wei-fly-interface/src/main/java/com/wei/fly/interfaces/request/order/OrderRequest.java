package com.wei.fly.interfaces.request.order;

import com.wei.fly.interfaces.annotation.RequiredParam;
import com.wei.fly.interfaces.request.BaseRequest;
import lombok.Data;

import java.util.Date;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Data
public class OrderRequest extends BaseRequest {

    /** 预约到店时间 */
    @RequiredParam
    private String useTime;

    /** 用户编号 */
    private String userId;

    /** f_seat表ID */
    @RequiredParam
    private Integer seatId;
}
