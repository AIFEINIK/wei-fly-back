package com.wei.fly.interfaces.response;

import com.wei.fly.interfaces.enums.ReturnStatusEnum;

import java.util.Collections;

public class Result<T> {

    private int status = ReturnStatusEnum.SUCCESS.getValue();
    private String message = ReturnStatusEnum.SUCCESS.getName();

    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int status, String message) {
        this.setStatus(status);
        this.message = message;
    }

    public Result(int status, String message, T data) {
        this.setStatus(status);
        this.message = message;
        this.data = data;
    }

    public static Result emptyPageResult() {
        return new Result(new Page(0, Collections.emptyList()));
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
