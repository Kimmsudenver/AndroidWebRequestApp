package com.kimtbui.shapeshiftprice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.kimtbui.shapeshiftprice.Data.Adapter.CoinAdapter;
import com.kimtbui.shapeshiftprice.Data.Adapter.RateAdapter;
import com.kimtbui.shapeshiftprice.Data.CoinData;
import com.kimtbui.shapeshiftprice.Data.Rate;
import com.kimtbui.shapeshiftprice.WebRequest.ShapeshiftRequest;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ShapeshiftRequest.ShapeshiftCallListener {

    @Bind(R.id.coinList) ListView coinListView;
    @Bind(R.id.chooseCoin) Spinner spinner;
    @Bind(R.id.trade) Button trade;
    static  ArrayList<Rate> coinDataList;
    static ArrayList<CoinData> listCoins;
    static HashMap<String,CoinData> listAvailableCoins;
    static RateAdapter rateAdapter;
    static CoinAdapter adapter;
    static Rate currentCoin;
    static LayoutInflater mInflater;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        mInflater = inflater;
        ButterKnife.bind(this,view);
        coinListView = (ListView) view.findViewById(R.id.coinList);
        spinner = (Spinner) view.findViewById(R.id.chooseCoin);
        coinDataList = new ArrayList<>();

        //Rate List
         rateAdapter = new RateAdapter(getContext(),android.R.layout.simple_list_item_1,coinDataList);
        rateAdapter.setInfalter(inflater);
        coinListView.setAdapter(rateAdapter);
        rateAdapter.setNotifyOnChange(true);

        //Spinner list
        listCoins = new ArrayList<>();



        ShapeshiftRequest request = new ShapeshiftRequest(this);
        request.getMarketInfo();
        request.getAllCoinData();

        return view;
    }

    private void setUpSpinner() {
        adapter = new CoinAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,listCoins);
        adapter.setInfalter(mInflater);
        spinner.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        spinner.setSelection(0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setDropDownWidth(-2);//wrap content

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onGetAllCoin(HashMap<String, CoinData> data) {
        ArrayList<String> CoinList = new ArrayList<String>(data.keySet());
       listAvailableCoins = new HashMap<>();
        listAvailableCoins = data;
        listCoins.addAll(data.values());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateImage();
                setUpSpinner();
            }
        });

    }

    @Override
    public void onGetMarketInfo(final HashMap<String, ArrayList<Rate>> result) {
            if(currentCoin == null)
                currentCoin = result.get("BTC").get(0); //random secCoin
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                coinDataList.addAll(result.get(currentCoin.getBaseCoin()));
                updateImage();
            }
        });
    }

    private void updateImage(){
        if(listAvailableCoins!=null) {
            for (Rate rate : coinDataList) {
                if(listAvailableCoins!=null)
                    if(listAvailableCoins.get(rate.getSecCoin())!=null)
                        rate.setImageLink(listAvailableCoins.get(rate.getSecCoin()).getImage());
            }
        }
    }
}
