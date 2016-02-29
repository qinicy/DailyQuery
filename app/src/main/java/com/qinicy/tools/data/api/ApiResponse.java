package com.qinicy.tools.data.api;

/**
 * Created by qinicy on 16/1/26.
 */
public class ApiResponse<T> {
    private T result;// 数据对象
    private int resultcode;    // 返回码，0为成功
    private String reason;      // 返回信息

    public int getResultcode() {
        return resultcode;
    }

    public ApiResponse<T> setResultcode(int resultcode) {
        this.resultcode = resultcode;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public ApiResponse<T> setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public T getResult() {
        return result;
    }

    public ApiResponse<T> setResult(T result) {
        this.result = result;
        return this;
    }


    public boolean isSuccess(){
        return resultcode == 200;
    }
}
