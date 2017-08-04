package com.jy.jz.zbx.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Mic-roo on 2016/11/28 0028.
 * 菜谱列表
 */

public class RecipeRoot implements Serializable{
    private int code;

    private String cause;

    private String total_result;

    private String page;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    private int page_size;

    private List<Data> data;


    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return this.cause;
    }

    public void setTotal_result(String total_result) {
        this.total_result = total_result;
    }

    public String getTotal_result() {
        return this.total_result;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPage() {
        return this.page;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getPage_size() {
        return this.page_size;
    }



}
