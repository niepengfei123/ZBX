package com.jy.jz.zbx.activity;

import java.util.List;

/**
 * Created by Mic-roo on 2016/12/5 0005.
 */
public class ImgData {
    private List<Imgs> imgs ;

    private List<ImgObjects> objects ;

    public void setImgs(List<Imgs> imgs){
        this.imgs = imgs;
    }
    public List<Imgs> getImgs(){
        return this.imgs;
    }
    public void setObjects(List<ImgObjects> objects){
        this.objects = objects;
    }
    public List<ImgObjects> getObjects(){
        return this.objects;
    }
}
