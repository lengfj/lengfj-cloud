package com.lengfj.cloud.common.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求头处理，对所有请求头进行转发
 * @author PC
 * @date
 */
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
	    HttpServletRequest httpServletRequest = getHttpServletRequest();
	    if (httpServletRequest == null) {
		    return;
	    }
	    Map<String, String> headers = getHeaders(httpServletRequest);
        for (String headerName : headers.keySet()) {
            requestTemplate.header(headerName, getHeaders(getHttpServletRequest()).get(headerName));
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes= RequestContextHolder.getRequestAttributes();

        if(requestAttributes instanceof ServletRequestAttributes){
            ServletRequestAttributes request = (ServletRequestAttributes)requestAttributes;
            return request.getRequest();
        }else{
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }
}
