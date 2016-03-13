package com.sam_chordas.android.stockhawk;


import com.sam_chordas.android.stockhawk.model.StockChart;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by divyamary on 13-01-2016.
 */
public interface StockChartApi {

    //type is either close or quote
    @GET("{symbol}/chartdata;type=close;range={range}/json")
    Call<StockChart> getStockChartData(@Path("symbol") String stockSymbol, @Path("range") String range);

}
