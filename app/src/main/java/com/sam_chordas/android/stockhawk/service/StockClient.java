package com.sam_chordas.android.stockhawk.service;

import android.util.Log;

import com.sam_chordas.android.stockhawk.rest.ApiManager;
import com.sam_chordas.android.stockhawk.rest.StockBus;
import com.sam_chordas.android.stockhawk.rest.StockChartResultEvent;
import com.sam_chordas.android.stockhawk.model.StockChart;
import com.sam_chordas.android.stockhawk.model.StockDetails;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StockClient {

    public void getStockChart(String stockSymbol, String range) {
        Log.d(StockClient.class.getSimpleName(), "Stock Chart Client");
        //"alternate_ranges": ["5d", "7d", "1m","3m","6m","1y","2y","5y","my"]
        Call<StockChart> call = ApiManager.getStockChartApi().getStockChartData(stockSymbol, range);
        call.enqueue(new Callback<StockChart>() {
            @Override
            public void onResponse(Call<StockChart> call, Response<StockChart> response) {
                if (response.isSuccessful()) {
                    Log.d("Success", "Retrofit success");
                    StockChart stockChart = response.body();
                    if (stockChart != null) {
                        StockBus.getInstance().post(new StockChartResultEvent(stockChart));
                    }
                } else {
                    int statusCode = response.code();
                    Log.e("Error", "Retrofit status code:" + statusCode);

                }
            }

            @Override
            public void onFailure(Call<StockChart> call, Throwable throwable) {
                Log.e("Failure", "Retrofit Failure" + throwable.getMessage());
                /*if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                ErrorBundle errorBundle = ErrorBundle.adapt(throwable);
                MovieBus.getInstance().post(new ErrorResultEvent(errorBundle));*/
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockDetails;
    }
}
