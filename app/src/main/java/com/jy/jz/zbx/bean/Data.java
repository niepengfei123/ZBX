package com.jy.jz.zbx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mic-roo on 2016/11/28 0028.
 */
public class Data implements Serializable {
    private String id;

    private String title;

    private String titlepic;

    private String author;

    private List<String> zhuliaoword;

    private String rate;

    private String gongyi;

    private String kouwei;

    private String step;

    private String make_time;

    private String make_diff;

    private String fav_num;

    private String onclick;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }

    public String getTitlepic() {
        return this.titlepic;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setString(List<String> zhuliaoword) {
        this.zhuliaoword = zhuliaoword;
    }

    public List<String> getString() {
        return this.zhuliaoword;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return this.rate;
    }

    public void setGongyi(String gongyi) {
        this.gongyi = gongyi;
    }

    public String getGongyi() {
        return this.gongyi;
    }

    public void setKouwei(String kouwei) {
        this.kouwei = kouwei;
    }

    public String getKouwei() {
        return this.kouwei;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStep() {
        return this.step;
    }

    public void setMake_time(String make_time) {
        this.make_time = make_time;
    }

    public String getMake_time() {
        return this.make_time;
    }

    public void setMake_diff(String make_diff) {
        this.make_diff = make_diff;
    }

    public String getMake_diff() {
        return this.make_diff;
    }

    public void setFav_num(String fav_num) {
        this.fav_num = fav_num;
    }

    public String getFav_num() {
        return this.fav_num;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnclick() {
        return this.onclick;
    }
}
