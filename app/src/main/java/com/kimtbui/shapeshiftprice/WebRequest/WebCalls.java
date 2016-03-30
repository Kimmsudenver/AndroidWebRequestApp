package com.kimtbui.shapeshiftprice.WebRequest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by kimbui on 3/19/16.
 */
public class WebCalls {
    WebCallListener webClientListener;
    static OkHttpClient webClient ;
    static final MediaType JSON = MediaType.parse("application/json; charset =utf-8");



    public WebCalls(WebCallListener listener){
        webClient = new OkHttpClient();
        webClientListener = listener;
    }


    interface WebCallListener{
        void onFailure(String message);
        void onGetResponse(Call call, Response response);
        void onPostResponse(Call call, Response response);
    }

    public void getRequest(String url){
        Request request = new Request.Builder().url(url).build();
        webClient =  new OkHttpClient();
        Call call = webClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                webClientListener.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                webClientListener.onGetResponse(call, response);
            }
        });
    }

    public void postRequest(String url, String params){
        RequestBody body = RequestBody.create(JSON, params);
        Request request = new Request.Builder().url(url).post(body).build();
        webClient =  new OkHttpClient();
        Call call = webClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                webClientListener.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                webClientListener.onPostResponse(call, response);
            }
        });
    }
}
