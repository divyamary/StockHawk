package com.sam_chordas.android.stockhawk;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ApiManager {

    private static final String CHART_BASE_URL = "http://chartapi.finance.yahoo.com/instrument/1.0/";
    private static final String YQL_FINANCE_BASE_URL = "https://query.yahooapis.com/v1/public/";

    private ApiManager() {

    }

    public static StockChartApi getStockChartApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CHART_BASE_URL)
                .addConverterFactory(JsonpGsonConverterFactory.create())
                .client(httpClient)
                .build();
        StockChartApi stockChartApi = retrofit.create(StockChartApi.class);
        return stockChartApi;
    }

    public static YQLFinanceAPI getStockApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YQL_FINANCE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        YQLFinanceAPI yqlFinanceAPI = retrofit.create(YQLFinanceAPI.class);
        return yqlFinanceAPI;
    }


}
