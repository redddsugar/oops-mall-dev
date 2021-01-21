package com.oops.enums;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月19日 14:57
 */
public enum  Sex {
    FEMALE(0, "女"),
    MALE(1, "男"),
    SECRET(2, "保密");

    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
