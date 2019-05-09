package com.wei.fly.interfaces.enums;

/**
 * @author Feinik
 * @Discription
 * @Data 2019/3/22
 * @Version 1.0.0
 */
public enum RoleTypeEnum {

    ADMIN(0, "管理员"),
    CUSTOMER(1, "普通用户"),
    MEMBER(2, "会员");

    private int index;
    private String value;

    RoleTypeEnum(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public static RoleTypeEnum getType(int index) {
        final RoleTypeEnum[] values = RoleTypeEnum.values();
        for (RoleTypeEnum value : values) {
            if (value.getIndex() == index) {
                return value;
            }
        }
        return null;
    }
}
