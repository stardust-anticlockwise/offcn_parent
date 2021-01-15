package com.offcn.response;

import com.offcn.enums.ResponseCodeEnume;

public class AppResponse<T> {

    private Integer code;
    private String message;
    private T data;

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

    public static<T> AppResponse<T> ok(T data){
        AppResponse<T> resp =new AppResponse<>();
        resp.setCode(ResponseCodeEnume.SUCCESS.getCode());
        resp.setMessage(ResponseCodeEnume.SUCCESS.getMessage());
        resp.setData(data);
        return resp;
    }

    public static<T> AppResponse<T> fail(T data){
        AppResponse<T> resp = new AppResponse<T>();
        resp.setCode(ResponseCodeEnume.FAIL.getCode());
        resp.setMessage(ResponseCodeEnume.FAIL.getMessage());
        resp.setData(data);
        return resp;
    }

}
