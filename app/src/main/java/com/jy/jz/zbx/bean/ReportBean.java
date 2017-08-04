package com.jy.jz.zbx.bean;

/**
 * Created by Mic-roo on 2016/11/24 0024.
 */

public class ReportBean {
    private int imgs;
    private String names;
    private String weight;

    public ReportBean(int imgs, String names, String weight) {
        this.imgs = imgs;
        this.names = names;
        this.weight = weight;
    }

    public int getImgs() {
        return imgs;
    }

    public void setImgs(int imgs) {
        this.imgs = imgs;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
