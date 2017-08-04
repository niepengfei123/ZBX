package com.jy.jz.zbx.activity;

/**
 * Created by Mic-roo on 2016/12/8 0008.
 * 图像识别解析
 */
public class ImgObjects {
    private String chineseIdeograph;

    private String createTime;

    private int expDay;

    private int id;

    private double imgBottom;

    private double imgLeft;

    private double imgRight;

    private double imgTop;

    private String name;

    private double probability;

    private int saveDays;

    private int userId;

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

    public void setExpDay(int expDay) {
        this.expDay = expDay;
    }

    public int getExpDay() {
        return this.expDay;
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

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getProbability() {
        return this.probability;
    }

    public void setSaveDays(int saveDays) {
        this.saveDays = saveDays;
    }

    public int getSaveDays() {
        return this.saveDays;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }
}
