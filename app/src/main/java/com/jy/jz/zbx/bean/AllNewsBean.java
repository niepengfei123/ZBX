package com.jy.jz.zbx.bean;

/**
 * Created by Mic-roo on 2016/11/25 0025.
 */

public class AllNewsBean {
    private String names;
    private String times;
    private String btn;

    public AllNewsBean(String names, String times, String btn) {
        this.names = names;
        this.times = times;
        this.btn = btn;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getBtn() {
        return btn;
    }

    public void setBtn(String btn) {
        this.btn = btn;
    }
}
