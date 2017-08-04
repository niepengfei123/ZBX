package com.jy.jz.zbx.activity;

/**
 * Created by Mic-roo on 2016/12/5 0005.
 */
public class Recognitioninfos {
    private String chineseIdeograph;

    private String createTime;

    private int id;

    private double imgBottom;

    private int imgId;

    private double imgLeft;

    private double imgRight;

    private double imgTop;

    private String name;

    private double probability;

    public void setChineseIdeograph(String chineseIdeograph) {
        this.chineseIdeograph = chineseIdeograph;
    }

    public String getChineseIdeograph() {
        return this.chineseIdeograph;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setImgBottom(double imgBottom) {
        this.imgBottom = imgBottom;
    }

    public double getImgBottom() {
        return this.imgBottom;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getImgId() {
        return this.imgId;
    }

    public void setImgLeft(double imgLeft) {
        this.imgLeft = imgLeft;
    }

    public double getImgLeft() {
        return this.imgLeft;
    }

    public void setImgRight(double imgRight) {
        this.imgRight = imgRight;
    }

    public double getImgRight() {
        return this.imgRight;
    }

    public void setImgTop(double imgTop) {
        this.imgTop = imgTop;
    }

    public double getImgTop() {
        return this.imgTop;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
