package com.oops.enums;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月19日 14:57
 */
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是");


    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
