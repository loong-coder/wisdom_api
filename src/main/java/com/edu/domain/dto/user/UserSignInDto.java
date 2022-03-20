package com.edu.domain.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class UserSignInDto {

    @NotBlank(message = "Email不能为空")
    private String email;

    private String name;

    private Boolean gender;

    @NotBlank(message = "手机号不能为空")
    private String phoneNo;

    @NotBlank(message = "密码 不能为空")
    private String passWord;

    @ApiModelProperty(value = "随机码")
    private String randomCode;

    @ApiModelProperty(value = "验证码")
    private String code;
}
