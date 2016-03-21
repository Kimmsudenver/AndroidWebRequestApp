package com.kimtbui.shapeshiftprice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.kimtbui.shapeshiftprice.Data.CoinAdapter;
import com.kimtbui.shapeshiftprice.Data.CoinData;
import com.kimtbui.shapeshiftprice.WebRequest.ShapeshiftRequest;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ListView coinListView;
    Spinner  spinner;
    ArrayList<CoinData> coinDataList;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        coinListView = (ListView) view.findViewById(R.id.coinList);
        spinner = (Spinner) view.findViewById(R.id.chooseCoin);
        coinDataList = new ArrayList<>();
        coinDataList.add(new CoinData("BTC"));
        coinDataList.add(new CoinData("LTC"));
        coinDataList.add(new CoinData("ETH"));
       // coinDataList = ShapeshiftRequest.get
        CoinAdapter coinAdapter = new CoinAdapter(getContext(),android.R.layout.simple_list_item_1,coinDataList);
        coinAdapter.setInfalter(inflater);
        coinAdapter.setNotifyOnChange(true);
        ShapeshiftRequest.getAllCoinData();
        coinListView.setAdapter(coinAdapter);

        setUpSpinner();


        return view;
    }

    private void setUpSpinner() {

        ArrayList<String> listCoins = new ArrayList<>();
        listCoins.add("BTC");
        listCoins.add("ETH");
        listCoins.add("LTC");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,listCoins);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

    }
}
