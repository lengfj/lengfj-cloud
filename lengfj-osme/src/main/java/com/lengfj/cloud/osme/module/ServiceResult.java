package com.lengfj.cloud.osme.module;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回结果
 *
 * @author lengfj
 * @version 1.0.0
 * @since 2021/12/23 3:41 PM
 */
@AllArgsConstructor
@Data
public class ServiceResult<T> {

    private Integer code;

    private boolean success;

    private T data;

    public ServiceResult(){
        this.code = 200;
        this.success = true;
    }
}
