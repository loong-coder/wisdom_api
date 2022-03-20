package com.edu.domain.request;

public class BaseRequest<T> {
    private T data;

    public BaseRequest() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
