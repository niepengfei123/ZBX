package com.jy.jz.zbx.bean;

/**
 * Created by Mic-roo on 2016/12/2 0002.
 */

public class MarketBean {
    private int imgs;
    private String name;
    private String pric;
    private String distance;

    public MarketBean(int imgs, String name, String pric, String distance) {
        this.imgs = imgs;
        this.name = name;
        this.pric = pric;
        this.distance = distance;
    }

    public int getImgs() {
        return imgs;
    }

    public void setImgs(int imgs) {
        this.imgs = imgs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPric() {
        return pric;
    }

    public void setPric(String pric) {
        this.pric = pric;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
