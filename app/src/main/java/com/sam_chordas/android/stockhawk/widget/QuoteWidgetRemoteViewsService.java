package com.sam_chordas.android.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.Utils;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

/**
 * Created by divyamary on 14-03-2016.
 */
public class QuoteWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new QuoteRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    private static class QuoteRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext = null;
        private Cursor mCursor = null;

        public QuoteRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
            // Since we reload the cursor in onDataSetChanged() which gets called immediately after
            // onCreate(), we do nothing here.
        }

        public void onDataSetChanged() {
            // Refresh the cursor
            if (mCursor != null) {
                mCursor.close();
            }
            mCursor = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                    new String[]{QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE,
                            QuoteColumns.PERCENT_CHANGE, QuoteColumns.CHANGE, QuoteColumns.ISUP},
                    QuoteColumns.ISCURRENT + " = ?",
                    new String[]{"1"},
                    null);
        }

        @Override
        public void onDestroy() {
            if (mCursor != null) {
                mCursor.close();
            }
        }

        @Override
        public int getCount() {
            return mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            mCursor.moveToPosition(position);
            String symbol = mCursor.getString(mCursor.getColumnIndex(QuoteColumns.SYMBOL));
            String change;
            if (Utils.showPercent) {
                change = mCursor.getString(mCursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE));
            } else {
                change = mCursor.getString(mCursor.getColumnIndex(QuoteColumns.CHANGE));
            }
            String bidPrice = mCursor.getString(mCursor.getColumnIndex(QuoteColumns.BIDPRICE));
            int isUp = mCursor.getInt(mCursor.getColumnIndex("is_up"));
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_collection_item);
            remoteViews.setTextViewText(R.id.stock_symbol, symbol);
            remoteViews.setTextViewText(R.id.bid_price, bidPrice);
            remoteViews.setTextViewText(R.id.change, change);
            if (isUp == 1) {
                remoteViews.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_green);
            } else {
                remoteViews.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
            }
            final Intent fillInIntent = new Intent();
            final Bundle extras = new Bundle();
            extras.putString(QuoteWidgetProvider.EXTRA_QUOTE, symbol);
            fillInIntent.putExtra("symbol", symbol);
            remoteViews.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
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

}
