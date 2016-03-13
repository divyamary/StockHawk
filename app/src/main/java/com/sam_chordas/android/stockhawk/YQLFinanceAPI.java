package com.sam_chordas.android.stockhawk;

import com.sam_chordas.android.stockhawk.model.StockDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by divyamary on 11-03-2016.
 */
public interface YQLFinanceAPI {

    //type is either close or quote
    @GET
    Call<StockDetails> getStockData(@Url String query, @Query("format") String format, @Query("diagnostics")
    String diagnostics, @Query(value = "env", encoded = true) String env, @Query("callback") String callback);
}
