package com.wei.fly.interfaces.request.seat;

import com.wei.fly.interfaces.enums.SeatStatusEnum;
import com.wei.fly.interfaces.enums.SeatTypeEnum;
import com.wei.fly.interfaces.request.PageRequest;
import lombok.Data;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/17
 * @Version 1.0.0
 */
@Data
public class ListSeatRequest extends PageRequest {

    private SeatTypeEnum seatType;
    private SeatStatusEnum locked;
    private String seatNum;
}
