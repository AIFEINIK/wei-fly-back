package com.wei.fly.interfaces.enums;

public enum ReturnStatusEnum {

    SUCCESS(200, "成功"),
    UNKNOWN(5000, "未知异常"),
    USER_NOT_EXISTS(5002, "用户不存在"),
    USER_PASSWD_INVALID(5003, "密码错误"),
    PERMISSION_INVALID(5004, "无权限操作"),
    SEAT_HAS_LOCKED(5005, "对不起，该座位已经被预约了"),
    SEAT_HAS_LOCKED_VIP(5006, "对不起，该座位已经被VIP用户预约了，无法解锁"),
    CODE_REQUIRED(5007, "code无效"),
    WX_LOGIN_FAILED(5008, "小程序登录失败"),
    FORBID_ORDER_TIME(5009, "请于22点后开始预约"),
    CARD_NOT_EXISTS(5010, "对不起，您还未领取会员卡，请先领取"),
    NOT_ENOUGH_BALANCE(5012, "对不起，您的会员卡余额不足，请联系商家充值"),
    ORDER_EXISTS(5013, "对不起，您已经预约了 {0} 的座位，不能在重复预约了"),
    NOT_VIP_USER(5014, "对不起，您不是会员用户，不支持在线预约"),
    CARD_NOT_ACTIVE(5015, "对不起，您的会员卡还未激活，请联系商家激活"),
    HAS_BIND_CARD(5016, "对不起，您已经绑定了会员卡，如需绑定其他类型会员卡，请先解绑"),
    NO_ORDER(5017, "对不起，您还未预约"),
    INVALID_USE_TIME(5018, "对不起，预约时间不能小于当前时间"),
    INVALID_TOKEN(5001, "token失效");

    private final int value;
    private final String name;

    ReturnStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
