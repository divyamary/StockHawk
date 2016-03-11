package com.sam_chordas.android.stockhawk;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sam_chordas.android.stockhawk.model.StockResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.Retrofit;

public class ApiManager {

    private static final String CHART_BASE_URL = "http://chartapi.finance.yahoo.com/instrument/1.0/";

    private ApiManager() {

    }

    public static StockApi getStockChartApi() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(StockResponse.class, new StockDeserializer())
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CHART_BASE_URL)
                .addConverterFactory(JsonpGsonConverterFactory.create())
                .client(httpClient)
                .build();
        StockApi stockApi = retrofit.create(StockApi.class);
        return stockApi;
    }


}
