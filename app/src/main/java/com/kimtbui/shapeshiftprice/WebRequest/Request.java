package com.kimtbui.shapeshiftprice.WebRequest;

import com.kimtbui.shapeshiftprice.Data.CoinData;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

/**
 * Created by kimbui on 3/9/16.
 */
public class Request {
    String baseURL = "https://shapeshift.io/api";
    String GET_COINS ="getcoins";
    String GET_PAIR_DATA = "marketinfo";




/**
 * Get all available coins with the data
 * */
    public ArrayList<CoinData> getAllcoinData(){
        ArrayList<CoinData> allData = new ArrayList<>();
        OkHttpClient webClient = new OkHttpClient();
        Request request = new Request.Builder()
        return allData;
    }


    /**
     * Get All prices of the coins with base unit is base coin
     * */
    public ArrayList<CoinData> getPricesForBaseCoin(CoinData){
        ArrayList<CoinData> dataForBaseCoin = new ArrayList<>();
        return dataForBaseCoin;
    }
}
