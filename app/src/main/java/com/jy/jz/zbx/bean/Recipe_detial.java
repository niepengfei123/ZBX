package com.jy.jz.zbx.bean;

import java.util.List;

/**
 * Created by Mic-roo on 2016/11/29 0029.
 */
public class Recipe_detial {
    private String id;

    private String title;

    private String onclick;

    private String kouwei;

    private String gongyi;

    private String rate;

    private String fav_num;

    private String make_time;

    private String make_pretime;

    private String make_diff;

    private String step;

    private String titlepic;

    private String smalltext;

    private Author author;

    private String newsphoto;

    private int newsphoto_h;

    private String liaos;

    private String zuofa;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setOnclick(String onclick){
        this.onclick = onclick;
    }
    public String getOnclick(){
        return this.onclick;
    }
    public void setKouwei(String kouwei){
        this.kouwei = kouwei;
    }
    public String getKouwei(){
        return this.kouwei;
    }
    public void setGongyi(String gongyi){
        this.gongyi = gongyi;
    }
    public String getGongyi(){
        return this.gongyi;
    }
    public void setRate(String rate){
        this.rate = rate;
    }
    public String getRate(){
        return this.rate;
    }
    public void setFav_num(String fav_num){
        this.fav_num = fav_num;
    }
    public String getFav_num(){
        return this.fav_num;
    }
    public void setMake_time(String make_time){
        this.make_time = make_time;
    }
    public String getMake_time(){
        return this.make_time;
    }
    public void setMake_pretime(String make_pretime){
        this.make_pretime = make_pretime;
    }
    public String getMake_pretime(){
        return this.make_pretime;
    }
    public void setMake_diff(String make_diff){
        this.make_diff = make_diff;
    }
    public String getMake_diff(){
        return this.make_diff;
    }
    public void setStep(String step){
        this.step = step;
    }
    public String getStep(){
        return this.step;
    }
    public void setTitlepic(String titlepic){
        this.titlepic = titlepic;
    }
    public String getTitlepic(){
        return this.titlepic;
    }
    public void setSmalltext(String smalltext){
        this.smalltext = smalltext;
    }
    public String getSmalltext(){
        return this.smalltext;
    }
    public void setAuthor(Author author){
        this.author = author;
    }
    public Author getAuthor(){
        return this.author;
    }
    public void setNewsphoto(String newsphoto){
        this.newsphoto = newsphoto;
    }
    public String getNewsphoto(){
        return this.newsphoto;
    }
    public void setNewsphoto_h(int newsphoto_h){
        this.newsphoto_h = newsphoto_h;
    }
    public int getNewsphoto_h(){
        return this.newsphoto_h;
    }

    public String getLiaos() {
        return liaos;
    }

    public void setLiaos(String liaos) {
        this.liaos = liaos;
    }

    public String getZuofa() {
        return zuofa;
    }

    public void setZuofa(String zuofa) {
        this.zuofa = zuofa;
    }
}
