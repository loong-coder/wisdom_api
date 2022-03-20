package com.edu.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AuthImageDto {

    @ApiModelProperty(value = "验证码图片Base64编码")
    private String captchaImg;

    @ApiModelProperty(value = "随机code 登录时和验证码信息一起发送到后端,用于校验验证码是否准确")
    private String randomCode;

    private String code;
}
