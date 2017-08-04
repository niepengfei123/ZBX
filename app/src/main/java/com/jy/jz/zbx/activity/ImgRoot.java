package com.jy.jz.zbx.activity;

import java.util.List;

/**
 * Created by Mic-roo on 2016/12/5 0005.
 */
public class ImgRoot {
    private ImgData data;

    private int code;

    private String msg;

    public void setData(ImgData data){
        this.data = data;
    }
    public ImgData getData(){
        return this.data;
    }
    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
}
