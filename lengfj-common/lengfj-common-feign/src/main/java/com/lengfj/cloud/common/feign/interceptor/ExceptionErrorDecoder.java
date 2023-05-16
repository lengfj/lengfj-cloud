package com.lengfj.cloud.common.feign.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lengfj.cloud.common.feign.FeignErrorInfo;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * feign接口异常信息打印
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/17
 **/
@Slf4j
public class ExceptionErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (response.body() != null) {
                FeignErrorInfo exceptionInfo = JSON.parseObject(
                        Util.toString(response.body().asReader()), new TypeReference<FeignErrorInfo>() {});
                log.error("\n系统内部异常:" +
                        "\n状态码:{};" +
                        "\n异常路径:{};" +
                        "\n异常信息:{}", exceptionInfo.getStatus(), exceptionInfo.getPath(), exceptionInfo.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FeignException.errorStatus(methodKey, response);
    }
}
