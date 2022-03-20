package com.edu.constant;

public enum RoleEnum {
    ADMIN(1),
    TEACHER(2),
    STUDENT(3);

    private Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }

    public Integer Value(){
        return code;
    }
}
