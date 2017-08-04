package com.jy.jz.zbx.bean;

import java.io.Serializable;

/**
 * Created by Mic-roo on 2016/12/10 0010.
 */

public class AddNames implements Serializable {
    private String name;

    public AddNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
