package com.wei.fly.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/5/2
 * @Version 1.0.0
 */
@Data
public class UserCard {
    private Date createTime;
    private Date updateTime;
    private String userId;
    private String cardCode;
    private Integer canUseTime;
    private Boolean active;
    private Integer cardType;
}
