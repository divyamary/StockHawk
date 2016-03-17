package com.sam_chordas.android.stockhawk.rest;

import com.sam_chordas.android.stockhawk.model.StockDetails;

/**
 * Created by divyamary on 12-03-2016.
 */
public class StockDetailsResultEvent {

    private StockDetails stockDetails;

    public StockDetailsResultEvent(StockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }

    public StockDetails getStockDetails() {
        return stockDetails;
    }

    public void setStockDetails(StockDetails stockDetails) {
        this.stockDetails = stockDetails;
    }
}
