package com.jy.jz.zbx.bean;

/**
 * Created by Mic-roo on 2016/11/29 0029.
 */

public class CookBookRoot {
    private int code;

    private String cause;

    private Recipe_detial recipe_detial;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setCause(String cause){
        this.cause = cause;
    }
    public String getCause(){
        return this.cause;
    }
    public void setRecipe_detial(Recipe_detial recipe_detial){
        this.recipe_detial = recipe_detial;
    }
    public Recipe_detial getRecipe_detial(){
        return this.recipe_detial;
    }
}
