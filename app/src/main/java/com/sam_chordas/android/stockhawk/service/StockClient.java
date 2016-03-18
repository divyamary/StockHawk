package com.sam_chordas.android.stockhawk.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.model.StockChart;
import com.sam_chordas.android.stockhawk.model.StockDetails;
import com.sam_chordas.android.stockhawk.rest.ApiManager;
import com.sam_chordas.android.stockhawk.rest.ErrorBundle;
import com.sam_chordas.android.stockhawk.rest.ErrorResultEvent;
import com.sam_chordas.android.stockhawk.rest.StockBus;
import com.sam_chordas.android.stockhawk.rest.StockChartResultEvent;
import com.squareup.otto.Bus;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StockClient {

    final String LOG_TAG = StockClient.class.getSimpleName();
    private Bus stockBus = StockBus.getInstance();
    private Context context;

    public StockClient(Context context) {
        this.context = context;
    }

    public void getStockChart(String stockSymbol, String range) {
        Log.d(LOG_TAG, "Stock Client");
        //"alternate_ranges": ["5d", "7d", "1m","3m","6m","1y","2y","5y","my"]
        Call<StockChart> call = ApiManager.getStockChartApi().getStockChartData(stockSymbol, range);
        call.enqueue(new Callback<StockChart>() {
            @Override
            public void onResponse(Call<StockChart> call, Response<StockChart> response) {
                if (response.isSuccessful()) {
                    Log.d(LOG_TAG, "Retrofit call successful");
                    StockChart stockChart = response.body();
                    if (stockChart != null) {
                        StockBus.getInstance().post(new StockChartResultEvent(stockChart));
                    }
                } else {
                    int statusCode = response.code();
                    Log.e(LOG_TAG, "Retrofit call not successful:" + statusCode);
                }
            }
            @Override
            public void onFailure(Call<StockChart> call, Throwable throwable) {
                Log.e(LOG_TAG, "Retrofit call Failure" + throwable.getMessage());
                ErrorBundle errorBundle = ErrorBundle.adapt(throwable);
                stockBus.post(new ErrorResultEvent(errorBundle, "ChartAPI"));
            }
        });

    }

    public StockDetails getStockQDetails(String stockSymbol) {
        StockDetails stockDetails = null;
        String query = "yql?q=select * from yahoo.finance.quotes where symbol " + "in (" + stockSymbol + "";
        Log.d("ENCODED URL:", query);
        String format = "json";
        String env = "store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        String callback = "";
        Call<StockDetails> call = ApiManager.getStockApi().getStockData(query, format, env, callback);
        try {
            stockDetails = call.execute().body();
            if (stockDetails != null && stockDetails.getQuery().getCount() == 1 &&
                    stockDetails.getQuery().getResults().getQuote().get(0).getStockExchange() == null) {
                final String message = context.getString(R.string.error_stock_name);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        stockBus.post(new ErrorResultEvent(message));
                    }
                });
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Retrofit call Failure" + e.getMessage());
            final ErrorBundle errorBundle = ErrorBundle.adapt(e);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    stockBus.post(new ErrorResultEvent(errorBundle, "YQL"));
                }
            });
        }
        return stockDetails;
    }
}
