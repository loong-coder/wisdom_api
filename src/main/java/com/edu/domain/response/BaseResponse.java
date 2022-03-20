package com.edu.domain.response;

public class BaseResponse<T> {
    private static final int CODE_SUCCESS = 200;

    private static final int CODE_FAIL = 500;

    private static final int CODE_ERROR = 500;

    private static final int CODE_NO_LOGIN = 300;

    private Integer code;

    private String message;

    private T data;

    public BaseResponse() {
    }

    public BaseResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<T>(CODE_SUCCESS, "success", null);
    }

    public static <T> BaseResponse<T> success(String message) {
        return new BaseResponse<T>(CODE_SUCCESS, message, null);
    }
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(CODE_SUCCESS, "success", data);
    }
    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<T>(CODE_SUCCESS, message, data);
    }

    public static <T> BaseResponse<T> failed() {
        return new BaseResponse<T>(CODE_ERROR, "fail", null);
    }
    public static <T> BaseResponse<T> failed(String message) {
        return new BaseResponse<T>(CODE_ERROR, message, null);
    }
    public static <T> BaseResponse<T> failed(T data) {
        return new BaseResponse<T>(CODE_ERROR, "fail", data);
    }
    public static <T> BaseResponse<T> failed(String message, T data) {
        return new BaseResponse<T>(CODE_ERROR, message, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
