package com.wei.fly.interfaces.enums;

/**
 * @author Feinik
 * @Discription 座位类型
 * @Data 2019/3/22
 * @Version 1.0.0
 */
public enum SeatTypeEnum {

    ONE_SEAT(1, "单座"),
    TWO_SEAT(2, "双座"),
    THREE_SEAT(3, "三座"),
    FOUR_SEAT(4, "四座");

    private int index;
    private String value;

    SeatTypeEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public static SeatTypeEnum getType(int index) {
        final SeatTypeEnum[] values = SeatTypeEnum.values();
        for (SeatTypeEnum value : values) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }
}
