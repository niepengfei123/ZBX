package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * Created by Mic-roo on 2017/7/6 0006.
 */

public class ProductBean implements Serializable{
   private  int code;
    private  String  msg;
    private  Product data;
    private  String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }
}
