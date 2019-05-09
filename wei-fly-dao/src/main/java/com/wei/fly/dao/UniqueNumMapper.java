package com.wei.fly.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/4/16
 * @Version 1.0.0
 */
public interface UniqueNumMapper {

    long getUniqueNum(@Param(value = "bizType") Integer bizType);
}
