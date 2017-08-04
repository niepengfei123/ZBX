package com.jy.jz.zbx.activity;

/**
 * Created by Mic-roo on 2016/12/8 0008.
 */
public class Imgs {
    private String addTime;

    private String brief;

    private String fileId;

    private int id;

    private int menuId;

    private int pid;

    private String url;

    public Imgs (int id){
        super();
        this.id=id;

    }

    public void setAddTime(String addTime){
        this.addTime = addTime;
    }
    public String getAddTime(){
        return this.addTime;
    }
    public void setBrief(String brief){
        this.brief = brief;
    }
    public String getBrief(){
        return this.brief;
    }
    public void setFileId(String fileId){
        this.fileId = fileId;
    }
    public String getFileId(){
        return this.fileId;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setMenuId(int menuId){
        this.menuId = menuId;
    }
    public int getMenuId(){
        return this.menuId;
    }
    public void setPid(int pid){
        this.pid = pid;
    }
    public int getPid(){
        return this.pid;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
}
