package com.sam_chordas.android.stockhawk;

import com.sam_chordas.android.stockhawk.model.StockChart;

/**
 * Created by divyamary on 10-03-2016.
 */
public class StockChartResultEvent {

    private StockChart mStockChart;

    public StockChartResultEvent(StockChart stockChart) {
        this.mStockChart = stockChart;
    }

    public StockChart getStockChart() {
        return mStockChart;
    }

    public void setStockChart(StockChart stockChart) {
        this.mStockChart = stockChart;
    }
}
