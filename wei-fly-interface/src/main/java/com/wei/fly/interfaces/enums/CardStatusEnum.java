package com.wei.fly.interfaces.enums;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/3/22
 * @Version 1.0.0
 */
public enum CardStatusEnum {

    NORMAL(0, "正常"),
    HAS_DEL(1, "已删除");

    private int index;
    private String value;

    CardStatusEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public static CardStatusEnum getType(int index) {
        final CardStatusEnum[] values = CardStatusEnum.values();
        for (CardStatusEnum value : values) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }
}
