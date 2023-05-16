//package com.lengfj.cloud.common.core.util.servlet;
//
//import org.apache.commons.lang3.StringUtils;
//import jakarta.servlet.http.HttpServletRequest;
//
//import static com.lengfj.cloud.common.core.constant.HeadersConstant.*;
//
///**
// * Created by A on 2018/12/28.
// */
//public class RequestCommonUtil {
//
//    /**
//     * 获取请求的token
//     */
//    public static String getRequestToken() {
//
//        HttpServletRequest httpRequest = ServletUtil.getRequest();
//        if (httpRequest == null){
//            return null;
//        }
//        return getRequestToken(httpRequest);
//    }
//    /**
//     * 获取请求的token
//     */
//    public static String getRequestToken(HttpServletRequest httpRequest) {
//        //从header中获取token
//        String token = httpRequest.getHeader(TOKEN);
//        //如果header中不存在token，则从参数中获取token
//        if (StringUtils.isBlank(token)) {
//            token = httpRequest.getParameter(TOKEN);
//        }
//        return token;
//    }
//
//    public static String getRequestSource(){
//        HttpServletRequest request = ServletUtil.getRequest();
//        if (request == null){
//            return null;
//        }
//        return getRequestSource(request);
//    }
//
//    /**
//     *
//     * 获取请求的数据来源
//     */
//    public static String getRequestSource(HttpServletRequest httpRequest) {
//        //从header中获取source
//        String source = httpRequest.getHeader(SOURCE);
//        //如果header中不存在token，则从参数中获取source
//        if (StringUtils.isBlank(source)) {
//            source = httpRequest.getParameter(SOURCE);
//        }
//        return source;
//    }
//
//    public static String getClientSource(){
//        HttpServletRequest request = ServletUtil.getRequest();
//        if (request == null){
//            return null;
//        }
//        return getClientSource(request);
//    }
//    /**
//     * 获取APP来源
//     * 1-安卓,2-ios，3-小程序，4-wap
//     * @param request
//     * @return
//     */
//    public static String getClientSource(HttpServletRequest request){
//        //从header中获取source
//        String clientSource = request.getHeader(CLIENT_SOURCE);
//        //如果header中不存在token，则从参数中获取source
//        if (StringUtils.isBlank(clientSource)) {
//            clientSource = request.getParameter(CLIENT_SOURCE);
//        }
//        return clientSource;
//    }
//
//    public static String getRequestChannel(){
//        HttpServletRequest request = ServletUtil.getRequest();
//        if (request == null){
//            return null;
//        }
//        return getRequestChannel(request);
//    }
//    /**
//     * 获取APP来源
//     * @param request
//     * @return
//     */
//    public static String getRequestChannel(HttpServletRequest request){
//        //从header中获取channel
//        String channel = request.getHeader(CHANNEL);
//        //如果header中不存在channel，则从参数中获取channel
//        if (StringUtils.isBlank(channel)) {
//            channel = request.getParameter(CHANNEL);
//        }
//        return channel;
//    }
//
//    public static Integer getSource(){
//        HttpServletRequest request = ServletUtil.getRequest();
//        if (request == null){
//            return null;
//        }
//        return getSource(request);
//    }
//    /**
//     * 获取来源
//     * @param request
//     * @return
//     */
//    public static Integer getSource(HttpServletRequest request){
//        String headerSource= request.getHeader(SOURCE);
//        String parameterSource= request.getParameter(SOURCE);
//        Integer source = null;
//        if(StringUtils.isEmpty(headerSource)){
//             if(StringUtils.isEmpty(parameterSource)){
//                 return null;
//             }else{
//                 source = Integer.valueOf(parameterSource);
//                 return source;
//             }
//        }else{
//            source = Integer.valueOf(headerSource);
//            return source;
//        }
//    }
//
//    public static String getRequestVersion(){
//        HttpServletRequest request = ServletUtil.getRequest();
//        if (request == null){
//            return null;
//        }
//        return getRequestVersion(request);
//    }
//
//    /**
//     * 获取请求的version
//     */
//    public static String getRequestVersion(HttpServletRequest httpRequest) {
//        //从header中获取version
//        String version = httpRequest.getHeader(VERSION);
//        //如果header中不存在version，则从参数中获取version
//        if (StringUtils.isBlank(version)) {
//            version = httpRequest.getParameter(VERSION);
//        }
//        return version;
//    }
//
//    public static String getRequestAccountToken(){
//        HttpServletRequest request = ServletUtil.getRequest();
//        if (request == null){
//            return null;
//        }
//        return getRequestAccountToken(request);
//    }
//
//    /**
//     * 获取请求的主用户token
//     */
//    public static String getRequestAccountToken(HttpServletRequest httpRequest) {
//        //从header中获取version
//        String accountToken = httpRequest.getHeader(ACCOUNT_TOKEN);
//        //如果header中不存在version，则从参数中获取version
//        if (StringUtils.isBlank(accountToken)) {
//            accountToken = httpRequest.getParameter(ACCOUNT_TOKEN);
//        }
//        return accountToken;
//    }
//
//    public static String getDeviceCode(){
//        HttpServletRequest request = ServletUtil.getRequest();
//        if (request == null){
//            return null;
//        }
//        return getDeviceCode(request);
//    }
//
//    /**
//     * 获取设备号
//     * @param request
//     * @return
//     */
//    public static String getDeviceCode(HttpServletRequest request){
//        //从header中获取deviceCode
//        String deviceCode = request.getHeader(DEVICE_CODE);
//        //如果header中不存在deviceCode，则从参数中获取deviceCode
//        if (StringUtils.isBlank(deviceCode)) {
//            deviceCode = request.getParameter(DEVICE_CODE);
//        }
//        return deviceCode;
//    }
//
//    public static String getPhoneModel(){
//        HttpServletRequest request = ServletUtil.getRequest();
//        if (request == null){
//            return null;
//        }
//        return getPhoneModel(request);
//    }
//
//    /**
//     * 获取手机型号
//     * @param request
//     * @return
//     */
//    public static String getPhoneModel(HttpServletRequest request){
//        //从header中获取phoneModel
//        String phoneModel = request.getHeader(PHONE_MODEL);
//        //如果header中不存在phoneModel，则从参数中获取phoneModel
//        if (StringUtils.isBlank(phoneModel)) {
//            phoneModel = request.getParameter(PHONE_MODEL);
//        }
//        return phoneModel;
//    }
//
//    public static String getIP(){
//        HttpServletRequest request = ServletUtil.getRequest();
//        if (request == null){
//            return null;
//        }
//        return getIP(request);
//    }
//
//    /**
//     * 获取IP
//     * @param request
//     * @return
//     */
//    public static String getIP(HttpServletRequest request){
//        String ip = request.getHeader("X-Forwarded-For");
//        if(StringUtils.isNotEmpty(ip) && !UN_KNOWN.equalsIgnoreCase(ip)){
//            //多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = ip.indexOf(",");
//            if(index != -1){
//                return ip.substring(0,index);
//            }else{
//                return ip;
//            }
//        }
//        ip = request.getHeader("X-Real-IP");
//        if(StringUtils.isNotEmpty(ip) && !UN_KNOWN.equalsIgnoreCase(ip)){
//            return ip;
//        }
//        return request.getRemoteAddr();
//    }
//}
