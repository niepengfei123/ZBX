package com.jy.jz.zbx.bean;

/**
 * Created by Mic-roo on 2016/11/29 0029.
 */
public class Lists {
    private String title;

    private String id;

    private String icon;

    private String num;

    private String category;

    private String is_c;
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setNum(String num){
        this.num = num;
    }
    public String getNum(){
        return this.num;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public String getCategory(){
        return this.category;
    }
    public void setIs_c(String is_c){
        this.is_c = is_c;
    }
    public String getIs_c(){
        return this.is_c;
    }
}
