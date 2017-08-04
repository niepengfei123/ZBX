package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * Created by Mic-roo on 2017/7/6 0006.
 */
public class Product implements Serializable{
    private Long id;
    private String productName;//产品名字
    private String productCode;//产品型号
    private String wifiCore;//wifi
    private String url;//H5地址

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getWifiCore() {
        return wifiCore;
    }

    public void setWifiCore(String wifiCore) {
        this.wifiCore = wifiCore;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
