package com.kimtbui.shapeshiftprice.Data.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kimtbui.shapeshiftprice.Data.CoinData;
import com.kimtbui.shapeshiftprice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kimbui on 3/27/16.
 */
public class CoinAdapter extends ArrayAdapter<CoinData> {

    private Context mContext;
    private LayoutInflater mInflate;
    @Bind(R.id.spinnerIcon) ImageView icon;
    @Bind(R.id.spinnerText) TextView name;

    public CoinAdapter(Context context, int resource, ArrayList<CoinData> coinDataSpinnerList) {
        super(context, resource, coinDataSpinnerList);
        mContext = context;
    }

    public void setInfalter(LayoutInflater inflate){
        mInflate = inflate;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = mInflate.inflate(R.layout.spinner_view, parent, false);
            ButterKnife.bind(this,view);

        } else {
            view = convertView;
        }
        ButterKnife.bind(this,view);

        CoinData currentCoin = this.getItem(position);
        name.setText(currentCoin.getName());
        Picasso.with(mContext).load(currentCoin.getImage()).into(icon);




        return view;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = mInflate.inflate(R.layout.spinner_view, parent, false);
            ButterKnife.bind(this,view);

        } else {
            view = convertView;
        }
        ButterKnife.bind(this,view);

        CoinData currentCoin = this.getItem(position);
        name.setText(currentCoin.getName());
        Picasso.with(mContext).load(currentCoin.getImage()).into(icon);




        return view;
    }
}
