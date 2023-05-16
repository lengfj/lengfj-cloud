package com.lengfj.cloud.common.core.enums;

/**
 * 获取来源
 * @author PC
 */
public enum SourceEnum {

    WAP(3, "H5"), FRONT(4, "商家端"), ADMIN(0, "管理后台"),
    APP(5, "app"), APPLET(6, "小程序"), FORAGE(7, "饲料专区");;

    /**
     * 描述
     */
    private String info;
    /**
     * 来源
     */
    private Integer source;

    SourceEnum(Integer source, String info) {
        this.source = source;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public Integer getSource() {
        return source;
    }

    public static String valBySource(Integer source){
        for (SourceEnum value : values()){
            if (value.getSource().equals(source)){
                return value.getInfo();
            }
        }
        return "";
    }

    public static SourceEnum valEnum(Integer source){
        for (SourceEnum value : values()){
            if (value.getSource().equals(source)){
                return value;
            }
        }
        return null;
    }

    public static String toStr() {
        StringBuilder sb = new StringBuilder("SourceEnum: ");
        for (SourceEnum value : values()){
            sb.append("{");
            sb.append("source=").append(value.getSource());
            sb.append(",info=").append(value.getInfo());
            sb.append("}, ");
        }
        return sb.toString();
    }
}
