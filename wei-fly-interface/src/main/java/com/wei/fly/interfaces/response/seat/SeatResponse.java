package com.wei.fly.interfaces.response.seat;

import com.wei.fly.interfaces.enums.SeatStatusEnum;
import com.wei.fly.interfaces.enums.SeatTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Data
public class SeatResponse implements Serializable {

    private Integer id;

    /** 创建时间 */
    private String createTime;

    /** 更新时间 */
    private String updateTime;

    /** 座位号 */
    private String num;

    /** 小标题 */
    private String title;

    /** 是否被预约（0:否，1:是） */
    private SeatStatusEnum locked;

    /** 座位类型（1:单座，2:双座） */
    private SeatTypeEnum seatType;

    /** 座位锁定结束时间（倒计时使用） */
    private String lockedEndTime;
}
