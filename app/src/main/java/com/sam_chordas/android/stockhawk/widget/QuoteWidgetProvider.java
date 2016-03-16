package com.sam_chordas.android.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

/**
 * Created by divyamary on 14-03-2016.
 */
public class QuoteWidgetProvider extends AppWidgetProvider {

    public static String CLICK_ACTION = "com.sam_chordas.android.stockhawk.widget.CLICK";
    public static String REFRESH_ACTION = "com.sam_chordas.android.stockhawk.widget.REFRESH";
    public static String EXTRA_QUOTE = "com.sam_chordas.android.stockhawk.widget.quote";
    public static final String ACTION_DATA_UPDATED =
            "com.sam_chordas.android.stockhawk.ACTION_DATA_UPDATED";



    @Override
    public void onReceive(Context ctx, Intent intent) {
        super.onReceive(ctx, intent);
        if (intent.getAction().equals(REFRESH_ACTION) || intent.getAction().equals(ACTION_DATA_UPDATED)) {
            Log.d("STOCK WIDGET", "received broadcast");
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
        // Set the empty view to be displayed if the collection is empty.  It must be a sibling
        // view of the collection view.
        rv.setEmptyView(R.id.widget_list, R.id.empty_view);

        // Bind the click intent for the refresh button on the widget
        final Intent refreshIntent = new Intent(context, QuoteWidgetProvider.class);
        refreshIntent.setAction(QuoteWidgetProvider.REFRESH_ACTION);
        final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
                refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(R.id.refresh, refreshPendingIntent);

        // Bind a click listener template for the contents of the weather list.  Note that we
        // need to update the intent's data if we set an extra, since the extras will be
        // ignored otherwise.
       /* final Intent onClickIntent = TaskStackBuilderProxyActivity.getTemplate(context);
        final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, 0,
                onClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.widget_list, onClickPendingIntent);*/

        final Intent openAppIntent = new Intent(context, MyStocksActivity.class);
        final PendingIntent openAppPendingIntent = PendingIntent.getActivity(context, 0,
                openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.widget_list, openAppPendingIntent);


        return rv;
    }
}


