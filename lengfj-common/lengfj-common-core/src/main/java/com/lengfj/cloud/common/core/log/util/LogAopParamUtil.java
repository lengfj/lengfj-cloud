package com.lengfj.cloud.common.core.log.util;

import com.alibaba.fastjson.JSONObject;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lester
 * @version V1.0
 * @date 2021/11/12
 **/
public class LogAopParamUtil {

    private final static int MAX_LENGTH = 2000;

    public static String getStringFromRequest(Object[] args){
        StringBuilder req = new StringBuilder();
        for(Object arg:args){
            if(arg == null){
                req.append("null,");
            }else if(arg instanceof List){
                String listStr = JSONObject.toJSONString(arg);
                req.append(listStr.length() > MAX_LENGTH ? listStr.substring(0, MAX_LENGTH)+"..." : listStr);
            }else if( arg instanceof Enum){
                req.append(JSONObject.toJSONString(arg));
                return req.toString();
            }else if(arg instanceof ServletRequest) {
                return req.toString();
            }else if(!(arg instanceof String) && !(arg instanceof BigDecimal) && !(arg instanceof Boolean) && !(arg instanceof Integer)){
                req.append(JSONObject.toJSONString(arg)).append(",");
            }else{
                req.append(arg.toString()).append(",");
            }
        }
        if (req.toString().endsWith(",")) {
            //去除多余逗号
            req = new StringBuilder(req.substring(0, req.length() - 1));
        }
        return req.toString();
    }

    public static String getStringFromResponse(Object arg){
        String rsp = "";
        if(arg == null){
            rsp = rsp + "null,";
            return rsp;
        }else if(arg instanceof List){
            String listStr = JSONObject.toJSONString(arg);
            rsp = rsp + (listStr.length() > MAX_LENGTH ? listStr.substring(0,MAX_LENGTH)+"..." : listStr);
            return rsp;
        }else if( arg instanceof Enum){
            rsp = rsp + JSONObject.toJSONString(arg);
            return rsp;
        }else if( arg instanceof ServletResponse) {
            return rsp.toString();
        }else if(!(arg instanceof String) && !(arg instanceof BigDecimal) && !(arg instanceof Boolean) && !(arg instanceof Integer)){
            rsp = rsp+JSONObject.toJSONString(arg);
        }else{
            rsp = rsp+arg.toString();
        }
        return rsp;
    }
}
