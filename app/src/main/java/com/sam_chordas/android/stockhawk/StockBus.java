package com.sam_chordas.android.stockhawk;

import com.squareup.otto.Bus;

/**
 * Created by divyamary on 13-01-2016.
 */
public class StockBus {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }
}
