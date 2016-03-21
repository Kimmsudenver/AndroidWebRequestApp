package com.kimtbui.shapeshiftprice.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimtbui.shapeshiftprice.R;
import com.kimtbui.shapeshiftprice.WebRequest.ShapeshiftRequest;

import java.util.ArrayList;
//TODO TODO convert to butterknife

/**
 * Created by kimbui on 3/6/16.
 */
public class CoinAdapter extends ArrayAdapter {

    LayoutInflater mInflate;
    TextView coinName,price;
    ImageView chart;
    Button tradeButton;
    CoinData btcCoin;


    public CoinAdapter(Context context, int resource, ArrayList<CoinData> coinDataList) {
        super(context, resource,coinDataList);
    }

    public void setInfalter(LayoutInflater inflate){
     mInflate = inflate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = mInflate.inflate(R.layout.coinview, parent, false);
        } else {
            view = convertView;
        }
        CoinData coin = (CoinData)this.getItem(position);
        coinName = (TextView) view.findViewById(R.id.coinName);
        coinName.setText(coin.getName());

        //set base coin is btc
        btcCoin = new CoinData("BTC");
        price = (TextView) view.findViewById(R.id.coinPrice);
       // price.setText((int) ShapeshiftRequest.getRate(coin,btcCoin));
        int result = (int) ShapeshiftRequest.getRate(coin,btcCoin);

        return view;

    }

}
