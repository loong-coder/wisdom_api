package com.edu.error;


import jdk.jfr.internal.LogLevel;

public class BusinessException extends RuntimeException {

    public Integer code;
    public String value;
    public LogLevel logLevel;
    public String clazzName;
    public String detailMessage;

    public String getMessage() {
        return this.detailMessage;
    }

    public BusinessException() {
        this.logLevel = LogLevel.INFO;
    }

    public BusinessException(Integer code, String value) {
        this.code = code;
        this.value = value;
        this.logLevel = LogLevel.INFO;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }

    public LogLevel getLogLevel() {
        return this.logLevel;
    }

    public String getClazzName() {
        return this.clazzName;
    }

    public String getDetailMessage() {
        return this.detailMessage;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }


    protected boolean canEqual(Object other) {
        return other instanceof BusinessException;
    }

    public String toString() {
        return "BaseException(code=" + this.getCode() + ", vaule=" + this.getValue() + ", logLevel=" + this.getLogLevel() + ", clazzName=" + this.getClazzName() + ", detailMessage=" + this.getDetailMessage() + ")";
    }
}
