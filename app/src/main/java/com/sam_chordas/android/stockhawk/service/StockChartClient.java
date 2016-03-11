package com.sam_chordas.android.stockhawk.service;

import android.util.Log;

import com.sam_chordas.android.stockhawk.ApiManager;
import com.sam_chordas.android.stockhawk.StockBus;
import com.sam_chordas.android.stockhawk.StockChartResultEvent;
import com.sam_chordas.android.stockhawk.model.StockResponse;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class StockChartClient {

    public void getStockChart(String stockSymbol) {
        Log.d(StockChartClient.class.getSimpleName(), "Stock Chart Client");
        //"alternate_ranges": ["5d","1m","3m","6m","1y","2y","5y","my"]
        Call<StockResponse> call = ApiManager.getStockChartApi().getStockChartData(stockSymbol, "5d");
        call.enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Response<StockResponse> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    Log.d("Success", "Retrofit success");
                    StockResponse stockResponse = response.body();
                    if (stockResponse != null) {
                        StockBus.getInstance().post(new StockChartResultEvent(stockResponse));
                    }
                } else {
                    int statusCode = response.code();
                    Log.e("Error", "Retrofit status code:" + statusCode);

                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("Failure", "Retrofit Failure" + throwable.getMessage());
                /*if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                ErrorBundle errorBundle = ErrorBundle.adapt(throwable);
                MovieBus.getInstance().post(new ErrorResultEvent(errorBundle));*/
            }
        });

    }
}
