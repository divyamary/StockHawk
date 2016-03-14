package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by divyamary on 14-03-2016.
 */
public class QuoteRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    List collections = new ArrayList();
    Context mContext = null;
    private Cursor mCursor;

    public QuoteRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        collections.clear();
        for (int i = 1; i <= 10; i++) {
            collections.add("ListView item " + i);
        }
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        mCursor.moveToPosition(position);
        String symbol = mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL));
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        remoteViews.setTextViewText(android.R.id.text1, symbol);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
