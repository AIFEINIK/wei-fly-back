package com.wei.fly.interfaces.enums;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/3/22
 * @Version 1.0.0
 */
public enum BizTypeEnum {

    ORDER(1, "预约订单"),
    CARD(2, "会员卡");

    private int index;
    private String value;

    BizTypeEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public static BizTypeEnum getType(int index) {
        final BizTypeEnum[] values = BizTypeEnum.values();
        for (BizTypeEnum value : values) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }
}
