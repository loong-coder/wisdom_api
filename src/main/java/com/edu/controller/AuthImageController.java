package com.edu.controller;

import cn.hutool.core.codec.Base64Encoder;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.edu.domain.dto.AuthImageDto;
import com.edu.domain.response.BaseResponse;
import com.edu.cache.CacheManager;
import com.edu.util.AuthCodeUtil;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码生成接口
 */
@Api(tags = "验证码生成接口")
@RestController
public class AuthImageController {

    @Resource
    private Producer producer;

    @Resource
    private CacheManager<String> cacheManager;

    @ApiOperation("获取验证码")
    @GetMapping(value = "captcha")
    public BaseResponse<AuthImageDto> captcha() throws IOException {

        String authCode = AuthCodeUtil.getAuthCode();
//        String authCode = "11111";

        BufferedImage image = producer.createImage(authCode);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        String imageFormat = "data:image/jpeg;base64,";
        String encode = Base64Encoder.encode(outputStream.toByteArray());

        String imageBase64Code = imageFormat + encode;
        AuthImageDto authImageDto = new AuthImageDto();
        authImageDto.setCaptchaImg(imageBase64Code);
        String randomCode = IdWorker.getIdStr();
        authImageDto.setRandomCode(randomCode);
        authImageDto.setCode(authCode);

        // 将 randomCode 和 authCode(验证码)缓存下来  用于之后验证前端传的验证码和后端保存的是否一致
        cacheManager.put(randomCode, authCode, 7L, TimeUnit.DAYS);

        return BaseResponse.success(authImageDto);
    }
}
