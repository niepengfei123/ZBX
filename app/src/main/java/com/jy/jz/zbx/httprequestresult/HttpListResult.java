package com.jy.jz.zbx.httprequestresult;

import java.util.ArrayList;
import java.util.List;

/**
 * /**
 * 返回的数据中最外层是数组或者list
 * @param <T>
 */
public class HttpListResult<T> {
    private boolean succ;

    private int code;

    private String msg;

    private List<T> data = new ArrayList<T>();

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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
