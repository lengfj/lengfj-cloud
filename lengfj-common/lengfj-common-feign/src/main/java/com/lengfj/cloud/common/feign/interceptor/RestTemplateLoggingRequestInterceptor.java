package com.lengfj.cloud.common.feign.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 *
 * 对restTemplate请求进行拦截，输出请求和响应日志
 * @author lester
 * @version V1.0
 * @date 2021/12/1
 **/
@Slf4j
public class RestTemplateLoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body){
        log.info("\n-------------请求开始-------------"
                +"\nURI         : {}"
                +"\nMethod      : {}"
                +"\nHeaders     : {}"
                +"\nRequest body: {}"
                +"\n-------------请求结束-------------",
                request.getURI(),
                request.getMethod(),
                request.getHeaders(),
                new String(body, StandardCharsets.UTF_8)
        );
//        log.info("URI         : {}", request.getURI());
//        log.info("Method      : {}", request.getMethod());
//        log.info("Headers     : {}", request.getHeaders());
//        log.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
//        log.info("-------------请求结束-------------");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            line = bufferedReader.readLine();
        }
        log.info("\n-------------应答开始-------------"
                +"\nStatus code  : {}"
                +"\nStatus text  : {}"
                +"\nHeaders      : {}"
                +"\nResponse body: {}"
                +"-------------应答结束-------------",
                response.getStatusCode(),
                response.getStatusText(),
                response.getHeaders(),
                inputStringBuilder.toString()
        );
//        log.info("Status code  : {}", response.getStatusCode());
//        log.info("Status text  : {}", response.getStatusText());
//        log.info("Headers      : {}", response.getHeaders());
//        log.info("Response body: {}", inputStringBuilder.toString());
//        log.info("-------------应答结束-------------");
    }
}
