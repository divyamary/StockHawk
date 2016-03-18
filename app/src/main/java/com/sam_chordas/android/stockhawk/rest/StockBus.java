package com.sam_chordas.android.stockhawk.rest;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by divyamary on 13-01-2016.
 */
public class StockBus {

    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);

    private StockBus() {

    }

    public static Bus getInstance() {
        return BUS;
    }
}
