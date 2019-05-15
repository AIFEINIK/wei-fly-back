package com.wei.fly.interfaces.request.card;

import com.wei.fly.interfaces.annotation.RequiredParam;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/9
 * @Version 1.0.0
 */
@Data
public class RechargeRequest {

    @RequiredParam
    private String cardCode;
    @RequiredParam
    private Integer useTime;
}

