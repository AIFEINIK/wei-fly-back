package com.wei.fly.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Data
public class Seat {

    /** 主键 */
    private Integer id;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 座位号 */
    private String num;

    /** 小标题 */
    private String title;

    /** 是否被预约（0:否，1:是） */
    private Integer locked;

    /** 座位类型（1:单座，2:双座） */
    private Integer seatType;

    /** 座位锁定结束时间（倒计时使用） */
    private String lockedEndTime;
}
