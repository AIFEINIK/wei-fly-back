package com.wei.fly.interfaces.enums;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/3/22
 * @Version 1.0.0
 */
public enum CardTypeEnum {

    WEEK(1, "周卡"),
    MONTH(2, "月卡"),
    QUARTER(3, "季度卡"),
    YEAR(4, "年卡");

    private int index;
    private String value;

    CardTypeEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public static CardTypeEnum getType(int index) {
        final CardTypeEnum[] values = CardTypeEnum.values();
        for (CardTypeEnum value : values) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }
}
