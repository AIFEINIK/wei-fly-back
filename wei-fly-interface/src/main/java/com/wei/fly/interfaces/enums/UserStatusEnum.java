package com.wei.fly.interfaces.enums;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/3/22
 * @Version 1.0.0
 */
public enum UserStatusEnum {

    NORMAL(0, "正常"),
    DELETE(-1, "已删除");

    private int index;
    private String value;

    UserStatusEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public static UserStatusEnum getType(int index) {
        final UserStatusEnum[] values = UserStatusEnum.values();
        for (UserStatusEnum value : values) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }
}
