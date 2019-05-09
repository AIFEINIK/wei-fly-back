package com.wei.fly.interfaces.enums;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/3/22
 * @Version 1.0.0
 */
public enum UserSexEnum {

    MAN(1, "男"),
    WOMAN(2, "女");

    private int index;
    private String value;

    UserSexEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public static UserSexEnum getType(int index) {
        final UserSexEnum[] values = UserSexEnum.values();
        for (UserSexEnum value : values) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }
}
