package com.wzf.tuojian.function.http.dto.response;

import android.text.TextUtils;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-04-17 17:13
 */

public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;

    public int getResultCode() {
        return code;
    }

    public void setResultCode(int resultCode) {
        this.code = resultCode;
    }

    public String getMsg() {
        return TextUtils.isEmpty(message) ? "" : message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }

    public T getResponse() {
        return data;
    }

    public void setResponse(T response) {
        this.data = response;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}