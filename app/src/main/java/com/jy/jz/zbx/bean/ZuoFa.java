package com.jy.jz.zbx.bean;

import java.util.List;

/**
 * Created by Mic-roo on 2016/11/29 0029.
 */
public class ZuoFa {
    private String d;

    private String d_img;

    private String dt;

    private String step;
    private int h;
    private int w;

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getD() {
        return this.d;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getDt() {
        return this.dt;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStep() {
        return this.step;
    }

    public String getD_img() {
        return d_img;
    }

    public void setD_img(String d_img) {
        this.d_img = d_img;
    }

}
