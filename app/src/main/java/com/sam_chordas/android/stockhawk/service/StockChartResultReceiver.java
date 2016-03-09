package com.sam_chordas.android.stockhawk.service;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;


/**
 * Created by divyamary on 09-03-2016.
 */
public class StockChartResultReceiver extends ResultReceiver {
    private Receiver mReceiver;

    public StockChartResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }

    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }


}
