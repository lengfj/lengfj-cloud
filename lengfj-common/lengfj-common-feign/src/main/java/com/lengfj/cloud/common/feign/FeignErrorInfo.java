package com.lengfj.cloud.common.feign;

import lombok.Data;

/**
 * Feign接口错误信息
 *
 * @author lester
 * @version V1.0
 * @date 2021/11/17
 **/
@Data
public class FeignErrorInfo {

    private Long timestamp;

    private Integer status;

    private String message;

    private String path;

    private String error;
}
