package com.edu.error;

import com.edu.domain.response.BaseResponse;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse exception(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return BaseResponse.failed(e.getMessage());
    }


    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse exception(BusinessException e) {
        log.error("全局异常信息 ex={}, code={}, value={}", e.getMessage(),e.getCode(), e.getValue(), e);
        BaseResponse<String> response = new BaseResponse<>();
        response.setCode(e.getCode());
        response.setMessage(e.getValue());
        return response;
    }


    @ExceptionHandler({StorageException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponse exception(StorageException e) {
        log.error("全局异常信息 ex={}, code={}, value={}", e.getMessage(),e.getCode(), e.getValue(), e);
        BaseResponse<String> response = new BaseResponse<>();
        response.setCode(e.getCode());
        response.setMessage(e.getValue());
        return response;
    }

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse exception(UnauthorizedException e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        BaseResponse<String> response = new BaseResponse<>();
        response.setCode(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(e.getMessage());
        return response;
    }
}
