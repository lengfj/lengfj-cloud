package com.lengfj.cloud.common.core.enums;


/**
 * 客户端枚举
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/23
 **/
public enum ClientSourceEnum {
    /**
     * 0:wap 1:android 2:ios 3:微信小程序
     */
    WAP(0,"wap"),
    ANDROID(1,"android"),
    IOS(2,"ios"),
    WX_APPLET(3,"微信小程序");


    private Integer type;

    private String desc;


    ClientSourceEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String valByType(Integer type){
        for (ClientSourceEnum value : values()){
            if (value.getType().equals(type)){
                return value.getDesc();
            }
        }
        return "";
    }

    public static String toStr() {
        StringBuilder sb = new StringBuilder("ClientSourceEnum: ");
        for (ClientSourceEnum value : values()){
            sb.append("{");
            sb.append("type=").append(value.getType());
            sb.append(",desc=").append(value.getDesc());
            sb.append("}, ");
        }
        return sb.toString();
    }
}
