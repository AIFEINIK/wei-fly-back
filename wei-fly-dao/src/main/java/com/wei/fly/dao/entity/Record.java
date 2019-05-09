package com.wei.fly.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/7
 * @Version 1.0.0
 */
@Data
public class Record {

    private Date createTime;

    private String orderCode;

    private String cardCode;

    private String userId;

    private Integer consumeNum;

    private String seatNum;

    private Integer seatType;

}
