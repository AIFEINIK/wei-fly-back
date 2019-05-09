package com.wei.fly.interfaces.response.wx;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Feinik
 * @Discription 微信code换取session
 * @Data 2019/4/27
 * @Version 1.0.0
 */
@Data
public class CodeResponse implements Serializable {

    /** 用户唯一标识 */
    private String openid;

    /** 会话密钥 */
    private String session_key;

    /** 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回 */
    private String unionid;

    /** errcode */
    private String errcode;

    /** 错误信息 */
    private String errmsg;
}
