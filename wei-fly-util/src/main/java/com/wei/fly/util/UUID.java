package com.wei.fly.util;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/3/21
 * @Version 1.0.0
 */
public class UUID {

    public UUID() {
    }

    public static String get() {
        return java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

}
