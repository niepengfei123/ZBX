package com.jy.jz.zbx.service;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;


/**
 * Created by Mic-roo on 2016/8/13 0013.
 * 操作数据库的实体类
 */

@Table(name = "address")
public class Address implements Serializable {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "startAdd")//NAME字段非空
    private String startAdd;

    @Column(name = "longitude")
    private String longitude;


    @Column(name = "latitude")
    private String latitude;

    @Column(name = "dimensionality")
    private String dimensionality;

    @Column(name = "city")
    private String city;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartAdd() {
        return startAdd;
    }

    public void setStartAdd(String startAdd) {
        this.startAdd = startAdd;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDimensionality() {
        return dimensionality;
    }

    public void setDimensionality(String dimensionality) {
        this.dimensionality = dimensionality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
