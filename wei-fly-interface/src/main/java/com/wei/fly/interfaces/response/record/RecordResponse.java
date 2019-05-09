package com.wei.fly.interfaces.response.record;

import com.wei.fly.interfaces.enums.SeatTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/7
 * @Version 1.0.0
 */
@Data
public class RecordResponse implements Serializable {

    private String createTime;

    private String orderCode;

    private String cardCode;

    private String userId;

    private Integer consumeNum;

    private String seatNum;

    private SeatTypeEnum seatType;

}
