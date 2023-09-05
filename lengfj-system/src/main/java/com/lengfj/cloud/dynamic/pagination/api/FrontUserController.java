package com.lengfj.cloud.dynamic.pagination.api;

import com.lengfj.cloud.common.core.api.R;
import com.lengfj.cloud.dynamic.pagination.common.FrontUserRegisterTypeEnum;
import com.lengfj.cloud.dynamic.pagination.vo.LoginInfoVO;
import com.lengfj.cloud.dynamic.pagination.vo.LoginRequestVO;
import com.lengfj.cloud.dynamic.pagination.vo.RegisterCaptchaVO;
import com.lengfj.cloud.dynamic.pagination.vo.RegisterFrontUserForEmailRequestVO;
import com.lengfj.cloud.dynamic.pagination.vo.UserInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端用户控制器
 *
 * @author CoDeleven
 */
@AllArgsConstructor
@Tag(name = "用户相关接口")
@RestController
@RequestMapping("/user")
public class FrontUserController {

    // private final FrontUserService frontUserService;

    @Operation(summary = "邮件验证回调")
    @GetMapping("/verify_email_code")
    public R<Void> verifyEmailCode(@Parameter(description = "邮箱验证码") @RequestParam("code") String code) {
        // frontUserService.verifyCode(FrontUserRegisterTypeEnum.EMAIL, code);
        return R.success("验证成功");
    }

    @Operation(summary = "邮箱注册")
    @PostMapping("/register/email")
    public R<Void> registerFrontUser(@Validated @RequestBody RegisterFrontUserForEmailRequestVO request) {
        // frontUserService.register(request);
        return R.success("注册成功");
    }

    @Operation(summary = "用户信息")
    @GetMapping("/info")
    public R<UserInfoVO> getUserInfo() {
        // return R.data(frontUserService.getLoginUserInfo());
        return R.success("");
    }

    @Operation(summary = "获取图片验证码")
    @GetMapping("/get_pic_code")
    public R<RegisterCaptchaVO> getPictureVerificationCode() {
        // return R.data(frontUserService.generateCaptcha());
        return R.success("");
    }

    @Operation(summary = "邮箱登录")
    @PostMapping("/login/email")
    public R<LoginInfoVO> login(@RequestBody LoginRequestVO request) {
        // return R.data(frontUserService.login(FrontUserRegisterTypeEnum.EMAIL, request.getUsername(), request.getPassword()));
        return R.success("");
    }
}