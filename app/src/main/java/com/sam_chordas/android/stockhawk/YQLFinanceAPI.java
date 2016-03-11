package com.sam_chordas.android.stockhawk;

import com.sam_chordas.android.stockhawk.model.StockQuery;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Url;

/**
 * Created by divyamary on 11-03-2016.
 */
public interface YQLFinanceAPI {

    //type is either close or quote
    @GET
    Call<StockQuery> getStockData(@Url String query, @Query("format") String format, @Query("diagnostics")
    String diagnostics, @Query(value = "env", encoded = true) String env, @Query("callback") String callback);
}
