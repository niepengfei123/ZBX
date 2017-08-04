package com.jy.jz.zbx.httprequestresult;

/**
 * 返回的数据中最外层不是数组或者list
 * @param <T>
 */
public class HttpResult<T> {
    private boolean succ;
    private int code;
    private String msg;
    private String ryUserId;
    private String ryToken;
    private String token;
//    private T data;
    private T userInfo;

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }


    public T getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(T userInfo) {
        this.userInfo = userInfo;
    }

    public String getRyUserId() {
        return ryUserId;
    }

    public void setRyUserId(String ryUserId) {
        this.ryUserId = ryUserId;
    }

    public String getRyToken() {
        return ryToken;
    }

    public void setRyToken(String ryToken) {
        this.ryToken = ryToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
