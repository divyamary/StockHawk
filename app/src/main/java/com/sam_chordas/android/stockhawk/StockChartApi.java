package com.sam_chordas.android.stockhawk;


import com.sam_chordas.android.stockhawk.model.StockResponse;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by divyamary on 13-01-2016.
 */
public interface StockChartApi {

    //type is either close or quote
    @GET("{symbol}/chartdata;type=close;range={range}/json")
    Call<StockResponse> getStockChartData(@Path("symbol") String stockSymbol, @Path("range") String range);

}
