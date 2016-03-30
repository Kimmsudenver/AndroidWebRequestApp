package com.kimtbui.shapeshiftprice.Data;

/**
 * Created by kimbui on 3/27/16.
 */
public class CoinDataWrapper {
    private String name;
    private CoinData coindata;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoinData getCoindata() {
        return coindata;
    }

    public void setCoindata(CoinData coindata) {
        this.coindata = coindata;
    }

    @Override
    public String toString() {
        return "CoinDataWrapper{" +
                "name='" + name + '\'' +
                ", coindata=" + coindata.toString() +
                '}';
    }
}
