package com.kimtbui.shapeshiftprice.WebRequest;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kimtbui.shapeshiftprice.Data.Adapter.DoubleTypeAdapter;
import com.kimtbui.shapeshiftprice.Data.CoinData;
import com.kimtbui.shapeshiftprice.Data.Rate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by kimbui on 3/9/16.
 */
public class ShapeshiftRequest implements WebCalls.WebCallListener{

    static final String LOG_TAG = "Shapeshift Requuest";
    static String baseURL = "https://shapeshift.io/";
   final  static String GET_COINS =baseURL + "getcoins";
    final static String MARKET_INFO = baseURL + "marketinfo";
    WebCalls webCalls;
    ShapeshiftCallListener shapeshiftCallListener;


    public interface ShapeshiftCallListener{
        void onFailure(String message);
        void onGetAllCoin(HashMap<String,CoinData> data);
        void onGetMarketInfo(HashMap<String,ArrayList<Rate>> result);
    }


    public ShapeshiftRequest(ShapeshiftCallListener listener)
    {
        webCalls = new WebCalls(this);
        shapeshiftCallListener = listener;
    }

/**
 * Get all available coins with the data
 * */
    public   void  getAllCoinData(){
        webCalls.getRequest(GET_COINS);
    }

    public void getMarketInfo(){
        webCalls.getRequest(MARKET_INFO);
    }


    /**
     * Get getUSD price of Coin
     * */
    public float getPrice(CoinData data){
       return 0;
    }


    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onGetResponse(Call call, Response response) {
        if(response.isSuccessful()){
            String result = null;
            try {
                result = response.body().string(

                );
                if(call.request().url().toString().equalsIgnoreCase(GET_COINS)){
                    shapeshiftCallListener.onGetAllCoin(jsontoAllCoinData(result));
                }
                else if(call.request().url().toString().equalsIgnoreCase(MARKET_INFO)){
                    /**return hashmap <coin, Arraylist<coindata>*/
                    shapeshiftCallListener.onGetMarketInfo(jsonToMarketData(result));


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{
            shapeshiftCallListener.onFailure(response.message());
        }

    }

    private HashMap<String,CoinData> jsontoAllCoinData(String json){
        HashMap<String,CoinData> coinDataList = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().create();
        Type coinDataType = new TypeToken<HashMap<String,CoinData>>(){}.getType();
        coinDataList = gson.fromJson(json,coinDataType);
     //   Log.d("COIN DATA :",coinDataList.toString());
        return coinDataList;

    }

    private HashMap<String,ArrayList<Rate>> jsonToMarketData(String result) {
        Log.d("MARKET","result for market" + result.toString());
        HashMap<String, ArrayList<Rate>> finalList = new HashMap<>();

        if(!result.isEmpty()) {
            Gson gson = new GsonBuilder().registerTypeAdapter(Double.class, new DoubleTypeAdapter()).serializeNulls().create();
            Type arrayCoinType = new TypeToken<ArrayList<Rate>>() {
            }.getType();
            ArrayList<Rate> rateList = gson.fromJson(result, arrayCoinType);
            for (Rate rate : rateList) {
                String pair = rate.getPair();
                String[] pairs = pair.split("_");
                rate.setBaseCoin(pairs[0]);
                rate.setSecCoin(pairs[1]);
                // Log.d("MARKET : " , rate.toString());
                if (!finalList.containsKey(pairs[0])) {
                    ArrayList<Rate> list = new ArrayList<>();
                    list.add(rate);
                    finalList.put(pairs[0], list);
                } else {
                    finalList.get(pairs[0]).add(rate);
                }
            }
        }else{
            shapeshiftCallListener.onFailure("No data fetched");
        }
        return finalList;

    }

    @Override
    public void onPostResponse(Call call, Response response) {

    }
}
