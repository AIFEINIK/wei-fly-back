package com.wei.fly.interfaces.request.order;

import com.wei.fly.interfaces.request.PageRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
@Data
public class ListOrderRequest extends PageRequest {

    private String orderCode;
    private String cardCode;
    private String userId;
    private Integer seatId;
}
