package com.oops.enums;

/**
 * @author L-N
 * @Description 支付方式
 */
public enum PayMethod {
    ALIPAY(1, "支付宝"),
    WECHAT(2, "微信");

    public final Integer type;
    public final String name;

    PayMethod(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
}
