package com.wei.fly.interfaces.enums;

/**
 * @author Feinik
 * @Discription 座位是否被预约
 * @Data 2019/3/22
 * @Version 1.0.0
 */
public enum SeatStatusEnum {

    NO(0, "否"),
    YES(1, "是");

    private int index;
    private String value;

    SeatStatusEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public static SeatStatusEnum getType(int index) {
        final SeatStatusEnum[] values = SeatStatusEnum.values();
        for (SeatStatusEnum value : values) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }
}
