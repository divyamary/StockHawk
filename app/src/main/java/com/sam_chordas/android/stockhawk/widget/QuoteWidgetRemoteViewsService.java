package com.sam_chordas.android.stockhawk.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by divyamary on 14-03-2016.
 */
public class QuoteWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        QuoteRemoteViewsFactory dataProvider = new QuoteRemoteViewsFactory(
                getApplicationContext(), intent);
        return dataProvider;
    }
}
