package com.example.gestionPrjt.Exception;

public enum ErrorCode {
    USER_NOT_FOUND(12000),
    USER_NOT_VALID(12001),
    USER_ALREADY_EXISTS(12002);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
