package com.sam_chordas.android.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.StockDetailsActivity;

/**
 * Created by divyamary on 14-03-2016.
 */
public class QuoteWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_DATA_UPDATED = "com.sam_chordas.android.stockhawk.ACTION_DATA_UPDATED";
    public static final String REFRESH_ACTION = "com.sam_chordas.android.stockhawk.widget.REFRESH";
    public static final String EXTRA_QUOTE = "com.sam_chordas.android.stockhawk.widget.quote";
    private final String LOG_TAG = QuoteWidgetProvider.class.getSimpleName();

    @Override
    public void onReceive(Context ctx, Intent intent) {
        super.onReceive(ctx, intent);
        if (intent.getAction().equals(REFRESH_ACTION) || intent.getAction().equals(ACTION_DATA_UPDATED)) {
            Log.d(LOG_TAG, "Received broadcast");
            final Context context = ctx;
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, QuoteWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widget_list);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Update each of the widgets with the remote adapter
        for (int widgetId : appWidgetIds) {
            RemoteViews layout = initViews(context, appWidgetManager, widgetId);
            appWidgetManager.updateAppWidget(widgetId, layout);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews initViews(Context context, AppWidgetManager widgetManager, int widgetId) {
        RemoteViews rv;
        final Intent intent = new Intent(context, QuoteWidgetRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        rv = new RemoteViews(context.getPackageName(), R.layout.widget_collection);
        rv.setRemoteAdapter(R.id.widget_list, intent);
        rv.setEmptyView(R.id.widget_list, R.id.empty_view);

        final Intent refreshIntent = new Intent(context, QuoteWidgetProvider.class);
        refreshIntent.setAction(QuoteWidgetProvider.REFRESH_ACTION);
        final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
                refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.refresh, refreshPendingIntent);

        final Intent openAppIntent = new Intent(context, StockDetailsActivity.class);
        PendingIntent openAppPendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(openAppIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.widget_list, openAppPendingIntent);
        return rv;
    }
}


