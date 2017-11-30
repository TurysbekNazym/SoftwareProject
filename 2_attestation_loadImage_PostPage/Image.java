package com.example.admin.menagmenttool;

import android.content.Intent;

/**
 * Created by admin on 20.11.2017.
 */

public class Image {

    public String name;
    public String url;
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image(){

    }
    public Image(String name, String url) {
        this.name = name;
        this.url = url;
    }


}
