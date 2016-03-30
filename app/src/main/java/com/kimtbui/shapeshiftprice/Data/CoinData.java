package com.kimtbui.shapeshiftprice.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by kimbui on 3/6/16.
 */
public class CoinData implements Parcelable, Serializable {

   private String name;

    public static final Creator<CoinData> CREATOR = new Creator<CoinData>() {
        @Override
        public CoinData createFromParcel(Parcel in) {
            return new CoinData(in);
        }

        @Override
        public CoinData[] newArray(int size) {
            return new CoinData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String symbol;
    private String image;
    private String status;


    @Override
    public String toString() {
        return "CoinData{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(symbol);
        dest.writeString(image);
        dest.writeString(status);

    }

    public CoinData(Parcel in){
        name = in.readString();
        symbol = in.readString();
        image = in.readString();
        status = in.readString();
    }
}
