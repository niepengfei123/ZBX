package com.jy.jz.zbx.bean;

/**
 * Created by Mic-roo on 2016/12/1 0001.
 */
public class Zuofas {
    private String d;

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
}
