package com.lengfj.cloud.common.mybatis.enums;

/**
 * 是否删除枚举
 * @author lester
 * @date 2022/1/19 3:53 下午
 */
public enum DelEnum {
    /**
     * 是否删除 1已删除 0未删除
     */
    YES(1),
    NO(0);
    private final Integer value;

    DelEnum(Integer value) {
        this.value = value;
    }

    public int val(){
        return this.value;
    }
}
