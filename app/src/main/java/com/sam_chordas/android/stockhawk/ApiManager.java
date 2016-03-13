package com.sam_chordas.android.stockhawk;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.sam_chordas.android.stockhawk.model.Quote;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




public class ApiManager {

    private static final String CHART_BASE_URL = "http://chartapi.finance.yahoo.com/instrument/1.0/";
    private static final String YQL_FINANCE_BASE_URL = "https://query.yahooapis.com/v1/public/";

    private ApiManager() {

    }

    public static StockChartApi getStockChartApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        //httpClient.interceptors().add(logging);
        //We use a custom JsonpGsonConverterFactory because response returned is in JSONP format.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CHART_BASE_URL)
                .addConverterFactory(JsonpGsonConverterFactory.create())
                .client(httpClient)
                .build();
        StockChartApi stockChartApi = retrofit.create(StockChartApi.class);
        return stockChartApi;
    }

    public static YQLFinanceAPI getStockApi() {
        Type myObjectListType = new TypeToken<List<Quote>>() {
        }.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(myObjectListType, new QuotesListTypeAdapter())
                .create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        //httpClient.interceptors().add(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YQL_FINANCE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build();
        YQLFinanceAPI yqlFinanceAPI = retrofit.create(YQLFinanceAPI.class);
        return yqlFinanceAPI;
    }

    /**
     * Quote returned can be either object or array.
     * http://stackoverflow.com/questions/7668507/gson-handle-object-or-array
     */
    private static class QuotesListTypeAdapter implements JsonDeserializer<List<Quote>> {
        @Override
        public List<Quote> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            List<Quote> quoteList = new ArrayList<>();
            if (json.isJsonArray()) {
                for (JsonElement jsonElement : json.getAsJsonArray()) {
                    quoteList.add((Quote) context.deserialize(jsonElement, Quote.class));
                }
            } else if (json.isJsonObject()) {
                quoteList.add((Quote) context.deserialize(json, Quote.class));
            }
            return quoteList;
        }
    }

}
