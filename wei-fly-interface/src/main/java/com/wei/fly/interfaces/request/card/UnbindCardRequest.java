package com.wei.fly.interfaces.request.card;

import com.wei.fly.interfaces.annotation.RequiredParam;
import com.wei.fly.interfaces.enums.CardTypeEnum;
import com.wei.fly.interfaces.request.BaseRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/5
 * @Version 1.0.0
 */
@Data
public class UnbindCardRequest extends BaseRequest {

    @RequiredParam
    private String cardCode;
}
