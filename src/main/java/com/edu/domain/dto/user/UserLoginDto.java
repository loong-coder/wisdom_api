package com.edu.domain.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDto {

    private String email;

    private String passWord;

    @ApiModelProperty(value = "随机码")
    private String randomCode;

    @ApiModelProperty(value = "验证码")
    private String code;
}
