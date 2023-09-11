package com.lengfj.cloud.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lengfj
 * @version 1.0
 * @date 2023/7/4
 **/
@Data
@Schema(title = "登录请求")
public class LoginRequestVO {

    @Schema(title = "邮箱地址")
    private String username;

    @Schema(title = "密码")
    private String password;
}
