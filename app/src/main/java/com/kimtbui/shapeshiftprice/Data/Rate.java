package com.kimtbui.shapeshiftprice.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kimbui on 3/26/16.
 */
public class Rate implements Parcelable{
        private double rate;
        private double limit;
        private String pair;
        private double maxLimit;
        private String baseCoin;
        private String secCoin;

        public String getImageLink() {
                return imageLink;
        }

        public void setImageLink(String imageLink) {
                this.imageLink = imageLink;
        }

        private String imageLink;

        public Rate(){
                String baseCoin = "BTC";
                this.baseCoin = baseCoin;
                this.setSecCoin(baseCoin);
                rate = 1;
                limit = 0;
                maxLimit = 0;
                min = 0;
                minerFee = 0;
                pair = baseCoin+"_"+baseCoin;

        }

        public Rate(String baseCoin){
                this.baseCoin = baseCoin;
                this.setSecCoin(baseCoin);
                rate = 1;
                limit = 0;
                maxLimit = 0;
                min = 0;
                minerFee = 0;
                pair = baseCoin+"_"+baseCoin;
                imageLink = "";
        }

        public Rate(double rate, double limit, String pair, double maxLimit, String baseCoin, String secCoin, double min, double minerFee,String iconLink) {
                this.rate = rate;
                this.limit = limit;
                this.pair = pair;
                this.maxLimit = maxLimit;
                this.baseCoin = baseCoin;
                this.secCoin = secCoin;
                this.min = min;
                this.minerFee = minerFee;
                this.imageLink = iconLink;
        }
        public Rate(Rate rate){

        }


        public static final Creator<Rate> CREATOR = new Creator<Rate>() {
                @Override
                public Rate createFromParcel(Parcel in) {
                        return new Rate(in);
                }

                @Override
                public Rate[] newArray(int size) {
                        return new Rate[size];
                }
        };

        public String getBaseCoin() {
                return baseCoin;
        }

        public void setBaseCoin(String baseCoin) {
                this.baseCoin = baseCoin;
        }

        public String getSecCoin() {
                return secCoin;
        }

        public void setSecCoin(String secCoin) {
                this.secCoin = secCoin;
        }

        public double getRate() {
                return rate;
        }

        public void setRate(double rate) {
                this.rate = rate;
        }

        public double getLimit() {
                return limit;
        }

        public void setLimit(double limit) {
                this.limit = limit;
        }

        public String getPair() {
                return pair;
        }

        public void setPair(String pair) {
                this.pair = pair;
        }

        public double getMaxLimit() {
                return maxLimit;
        }

        public void setMaxLimit(double maxLimit) {
                this.maxLimit = maxLimit;
        }

        public double getMin() {
                return min;
        }

        public void setMin(double min) {
                this.min = min;
        }

        public double getMinerFee() {
                return minerFee;
        }

        public void setMinerFee(double minerFee) {
                this.minerFee = minerFee;
        }

        private double min;
        private double minerFee;


        @Override
        public String toString() {
                return "Rate{" +
                        "rate=" + rate +
                        ", limit=" + limit +
                        ", pair='" + pair + '\'' +
                        ", maxLimit=" + maxLimit +
                        ", baseCoin='" + baseCoin + '\'' +
                        ", secCoin='" + secCoin + '\'' +
                        ", min=" + min +
                        ", minerFee=" + minerFee +
                        '}';
        }


        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(pair);
                dest.writeDouble(rate);
                dest.writeDouble(limit);
                dest.writeDouble(maxLimit);
                dest.writeDouble(min);
                dest.writeDouble(minerFee);
        }

        Rate(Parcel in){
                this.pair = in.readString();
                rate = in.readDouble();
                limit = in.readDouble();
                maxLimit = in.readDouble();
                min = in.readDouble();
                minerFee = in.readDouble();

        }


}
