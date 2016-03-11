package com.sam_chordas.android.stockhawk;

import com.sam_chordas.android.stockhawk.model.StockResponse;

/**
 * Created by divyamary on 10-03-2016.
 */
public class StockChartResultEvent {

    private StockResponse stockResponse;

    public StockChartResultEvent(StockResponse stockResponse) {
        this.stockResponse = stockResponse;
    }

    public StockResponse getStockResponse() {
        return stockResponse;
    }

    public void setStockResponse(StockResponse stockResponse) {
        this.stockResponse = stockResponse;
    }
}
