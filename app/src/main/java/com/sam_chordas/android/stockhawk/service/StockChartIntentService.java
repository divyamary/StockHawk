package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class StockChartIntentService extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    private OkHttpClient client = new OkHttpClient();

    public StockChartIntentService(){
        super(StockChartIntentService.class.getName());
    }


    public StockChartIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(StockChartIntentService.class.getSimpleName(), "Stock Chart Intent Service");
        ResultReceiver resultReceiver = intent.getParcelableExtra("receiverTag");
        Bundle bundle = new Bundle();
        String stockSymbol = intent.getStringExtra("symbol");
        StringBuilder urlStringBuilder = new StringBuilder();
        try {
            // Base URL for the Yahoo query

            urlStringBuilder.append("https://query.yahooapis.com/v1/public/yql?q=");
            urlStringBuilder.append("select * from yahoo.finance.historicaldata where symbol%20%3D");
            // finalize the URL for the API query.
            urlStringBuilder.append(URLEncoder.encode("\""+stockSymbol+"\" and startDate = \"2015-09-11\" and endDate = \"2016-03-08\"", "UTF-8"));
            urlStringBuilder.append("&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables."
                    + "org%2Falltableswithkeys&callback=");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String urlString;
        if (urlStringBuilder != null) {
            urlString = urlStringBuilder.toString();
            Log.d("CHART URL:",urlString);
            Request request = new Request.Builder()
                    .url(urlString)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();
                //Log.d("RESPONSE CHART:",responseBody);
                /* Sending result back to activity */
                if (null != responseBody && responseBody.length() > 0) {
                    bundle.putString("result", responseBody);
                    resultReceiver.send(STATUS_FINISHED, bundle);
                }
            } catch (IOException e) {
                /* Sending error message back to activity */
                bundle.putString(Intent.EXTRA_TEXT, e.toString());
                resultReceiver.send(STATUS_ERROR, bundle);
                e.printStackTrace();
            }

        }
    }
}
