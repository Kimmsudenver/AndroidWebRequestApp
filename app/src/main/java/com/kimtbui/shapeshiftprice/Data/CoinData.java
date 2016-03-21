package com.kimtbui.shapeshiftprice.Data;

import android.media.Image;

/**
 * Created by kimbui on 3/6/16.
 */
public class CoinData {

    public CoinData(String inName, Image image){
        name = inName;
      //  price = inPrice;
        icon = image;


    }

    public CoinData(String inName){
        name = inName;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    private Image icon;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
