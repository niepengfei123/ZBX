package com.jy.jz.zbx.bean;

/**
 * Created by Mic-roo on 2016/11/24 0024.
 */

public class CookBookBean {
    private int img;
    private String name;

    public CookBookBean(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
