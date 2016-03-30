package com.kimtbui.shapeshiftprice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.github.clans.fab.FloatingActionButton;
import com.kimtbui.shapeshiftprice.Data.Adapter.CoinAdapter;
import com.kimtbui.shapeshiftprice.Data.Adapter.RateAdapter;
import com.kimtbui.shapeshiftprice.Data.CoinData;
import com.kimtbui.shapeshiftprice.Data.Rate;
import com.kimtbui.shapeshiftprice.WebRequest.ShapeshiftRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ShapeshiftRequest.ShapeshiftCallListener ,AdapterView.OnItemSelectedListener
        , View.OnClickListener{

    private static final String RATE_LIST ="rateList" ;
    private static final String COIN_LIST ="coinList" ;
    private static final String ALL_RATE_LIST ="allRateList" ;
    private static final String CURRENT_COIN ="currentCoin" ;

    @Bind(R.id.coinList) ListView coinListView;
    @Bind(R.id.chooseCoin) Spinner spinner;
    @Bind(R.id.refresh)
    FloatingActionButton refresh;
    static  ArrayList<Rate> rateDataListOfBaseCoin;
    static ArrayList<CoinData> coinDataList;
    static HashMap<String,CoinData> listAvailableCoins;
    static RateAdapter rateAdapter;
    static CoinAdapter adapter;
    //static Rate currentRate;
    static CoinData currentCoin;
    static LayoutInflater mInflater;
    static HashMap<String,ArrayList<Rate>> allRateDataMap;
    static ShapeshiftRequest request;
    static boolean didRefresh = false;
     Handler handler ;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.floating_button, container, false);
        mInflater = inflater;
        ButterKnife.bind(this,view);
        coinListView = (ListView) view.findViewById(R.id.coinList);
        spinner = (Spinner) view.findViewById(R.id.chooseCoin);
        if(savedInstanceState !=null) {
            if (savedInstanceState.containsKey(RATE_LIST))
                rateDataListOfBaseCoin = (ArrayList<Rate>) savedInstanceState.getSerializable(RATE_LIST);
            if (savedInstanceState.containsKey(COIN_LIST))
                coinDataList = (ArrayList<CoinData>) savedInstanceState.getSerializable(COIN_LIST);
            if (savedInstanceState.containsKey(CURRENT_COIN))
                currentCoin = (CoinData) savedInstanceState.getSerializable(CURRENT_COIN);
            if (savedInstanceState.containsKey(ALL_RATE_LIST))
                allRateDataMap = (HashMap<String, ArrayList<Rate>>) savedInstanceState.getSerializable(ALL_RATE_LIST);
        }

        if(rateDataListOfBaseCoin==null)
            rateDataListOfBaseCoin = new ArrayList<>();

        //Spinner list
        if(coinDataList==null)
            coinDataList = new ArrayList<>();

        refresh.setOnClickListener(this);


        //Rate List
        rateAdapter = new RateAdapter(getContext(),android.R.layout.simple_list_item_1, rateDataListOfBaseCoin);
        rateAdapter.setInfalter(mInflater);
        rateAdapter.setNotifyOnChange(true);
        coinListView.setAdapter(rateAdapter);

        //spinner
        adapter = new CoinAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, coinDataList);
        adapter.setInfalter(mInflater);
        spinner.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setDropDownWidth(-2);//wrap content
        spinner.setOnItemSelectedListener(this);

        if(currentCoin!=null) updateSpinner();

         request = new ShapeshiftRequest(this);
        if(rateDataListOfBaseCoin==null ||coinDataList==null || currentCoin == null || allRateDataMap == null || listAvailableCoins == null) {
            request.getMarketInfo();
            request.getAllCoinData();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RATE_LIST,rateDataListOfBaseCoin);
        outState.putSerializable(COIN_LIST,coinDataList);
        outState.putSerializable(ALL_RATE_LIST,allRateDataMap);
        outState.putSerializable(CURRENT_COIN,currentCoin);
    }

    private void updateRateList(){
        spinner.setSelection(coinDataList.indexOf(currentCoin));
        rateAdapter.notifyDataSetChanged();
        didRefresh = false;
    }

    private void updateSpinner() {
        adapter.notifyDataSetChanged();
        spinner.setSelection(coinDataList.indexOf(currentCoin));


    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void onGetAllCoin(HashMap<String, CoinData> data) {
        ArrayList<String> CoinList = new ArrayList<String>(data.keySet());
       listAvailableCoins = new HashMap<>();
        listAvailableCoins = data;
        coinDataList.addAll(data.values());
        if(currentCoin ==null)
            currentCoin = getCurrentCoin("BTC");
        if(getActivity()!=null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateImage();
                    updateSpinner();
                }
            });
        }
    }

    @Override
    public void onGetMarketInfo(final HashMap<String, ArrayList<Rate>> result) {
        didRefresh = true;
            if(currentCoin == null) {
                currentCoin = getCurrentCoin("BTC");
            }
        if(!rateDataListOfBaseCoin.isEmpty()) rateDataListOfBaseCoin.clear();
        rateDataListOfBaseCoin.addAll(result.get(currentCoin.getSymbol()));
        allRateDataMap = result;
        if(getActivity()!=null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateRateList();
                    updateImage();
                    updateSpinner();
                }
            });
        }

    }

    private CoinData getCurrentCoin(String symbol) {
        for(CoinData coin : coinDataList){
            if(coin.getSymbol().equalsIgnoreCase(symbol))
                return coin;
        }
        return null;
    }

    private void updateImage(){
        if(listAvailableCoins!=null) {
            for (Rate rate : rateDataListOfBaseCoin) {
                if(listAvailableCoins!=null)
                    if(listAvailableCoins.get(rate.getSecCoin())!=null)
                        rate.setImageLink(listAvailableCoins.get(rate.getSecCoin()).getImage());
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            currentCoin = coinDataList.get(position);
        if(currentCoin!=null && allRateDataMap!=null && currentCoin.getSymbol()!=null&& rateDataListOfBaseCoin!=null) {
            if(allRateDataMap.get(currentCoin.getSymbol())!=null) {
                rateDataListOfBaseCoin.clear();
                rateDataListOfBaseCoin.addAll(allRateDataMap.get(currentCoin.getSymbol()));
            }
            if(rateDataListOfBaseCoin != null && !rateDataListOfBaseCoin.isEmpty())
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rateAdapter.notifyDataSetChanged();
                        updateImage();
                    }
                });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.refresh){

            refresh();
        }
    }

    private void refresh() {
        if(!didRefresh) {
          //  currentCoin = getCurrentCoin("BTC");
            request.getMarketInfo();
        }

    }
}
