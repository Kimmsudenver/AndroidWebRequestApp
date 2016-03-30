package com.kimtbui.shapeshiftprice.Data.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimtbui.shapeshiftprice.Data.Rate;
import com.kimtbui.shapeshiftprice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
//TODO TODO convert to butterknife

/**
 * Created by kimbui on 3/6/16.
 */
public class RateAdapter extends ArrayAdapter<Rate> {

    LayoutInflater mInflate;
    static Context mContext;
    Rate btcCoin;
    @Bind(R.id.coinName)  TextView coinName;
    @Bind(R.id.rate) TextView rate;
    @Bind(R.id.min) TextView min;
    @Bind(R.id.max) TextView max;
    @Bind(R.id.icon) ImageView icon;



    public RateAdapter(Context context, int resource, ArrayList<Rate> coinDataList) {
        super(context, resource,coinDataList);
        mContext = context;
    }

    public void setInfalter(LayoutInflater inflate){
     mInflate = inflate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = mInflate.inflate(R.layout.coinview, parent, false);
            ButterKnife.bind(this,view);

        } else {
            view = convertView;
        }
        ButterKnife.bind(this,view);

        Rate coin = (Rate)this.getItem(position);
      //  coinName = (TextView) view.findViewById(R.id.coinName);
        coinName.setText(coin.getSecCoin());
        rate.setText(String.valueOf(coin.getRate()));
        min.setText(String.valueOf(coin.getMin()));
        max.setText(String.valueOf(coin.getMaxLimit()));
        if(coin.getImageLink()!=null)
            Picasso.with(mContext).load(coin.getImageLink()).into(icon);



        return view;

    }

}
