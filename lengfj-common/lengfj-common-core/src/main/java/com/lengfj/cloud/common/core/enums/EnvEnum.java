package com.lengfj.cloud.common.core.enums;


/**
 * 项目环境枚举
 * @author lester
 */
public enum EnvEnum {

    DEV("1", "开发环境", "dev"),
    TEST("2", "测试环境", "test"),
    PRE("3", "预发布环境", "pre"),
    PROD("4", "生产环境", "prod");

    private String flag;
    private String envName;
    private String env;

    EnvEnum(String flag, String envName, String env) {
        this.flag = flag;
        this.envName = envName;
        this.env = env;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public static EnvEnum valByFlag(String flag){
        for (EnvEnum value : values()){
            if (value.getFlag().equals(flag)){
                return value;
            }
        }
        return null;
    }

    public static EnvEnum valByEnv(String env){
        for (EnvEnum value : values()){
            if (value.getEnv().equals(env)){
                return value;
            }
        }
        return null;
    }
}
