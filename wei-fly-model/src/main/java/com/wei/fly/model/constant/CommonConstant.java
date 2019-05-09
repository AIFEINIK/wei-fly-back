package com.wei.fly.model.constant;

/**
 * @author Feinik
 * @Discription 常量配置
 * @Data 2019/3/20
 * @Version 1.0.0
 */
public class CommonConstant {

    public static final String WX_CODE_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=wx9d43fcc5f20ce493&secret=8dc783f80a78117ac02ad99c806455e2&js_code={0}&grant_type=authorization_code";

    public static final String ACCOUNT_WEB_APPLICATION_NAME = "wei-fly-web";
    public static final String TOKEN_KEY = "token";
    public static final int TOKEN_TIMEOUT = 24 * 60 * 60;
    public static final String PASSWD_SOLT = "feinik";


    public static final String ORDER_CODE_PREFIX = "OR";
    public static final String CARD_CODE_PREFIX = "C";

    /**
     * 跨域配置
     */
    final public static String CORS_ALLOW_ORIGIN = "cors.allowOrigin";
    final public static boolean CORS_ALLOW_ORIGIN_ENABLE = true;
}
