package com.sam_chordas.android.stockhawk.service;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.model.StockDetails;
import com.sam_chordas.android.stockhawk.rest.Utils;

/**
 * Created by sam_chordas on 9/30/15.
 * The GCMTask service is primarily for periodic tasks. However, OnRunTask can be called directly
 * and is used for the initialization and adding task as well.
 */
public class StockTaskService extends GcmTaskService{
  private String LOG_TAG = StockTaskService.class.getSimpleName();
  private Context mContext;
  private StringBuilder mStoredSymbols = new StringBuilder();
  private boolean isUpdate;
  private String stockSymbol;
  public static final String ACTION_DATA_UPDATED =
          "com.sam_chordas.android.stockhawk.ACTION_DATA_UPDATED";

  public StockTaskService(){}

  public StockTaskService(Context context){
    mContext = context;
  }

  @Override
  public int onRunTask(TaskParams params){
    Log.d("GCM", "ON RUN");
    Cursor initQueryCursor;
    if (mContext == null){
      mContext = this;
    }
    if (params.getTag().equals("init") || params.getTag().equals("periodic")){
      isUpdate = true;
      initQueryCursor = mContext.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
          new String[] { "Distinct " + QuoteColumns.SYMBOL }, null,
          null, null);
      if (initQueryCursor.getCount() == 0 || initQueryCursor == null){
        // Init task. Populates DB with quotes for the symbols seen below
        stockSymbol = "\"YHOO\",\"AAPL\",\"GOOG\",\"MSFT\")";
      } else if (initQueryCursor != null){
        DatabaseUtils.dumpCursor(initQueryCursor);
        initQueryCursor.moveToFirst();
        for (int i = 0; i < initQueryCursor.getCount(); i++){
          mStoredSymbols.append("\""+
              initQueryCursor.getString(initQueryCursor.getColumnIndex("symbol"))+"\",");
          initQueryCursor.moveToNext();
        }
        mStoredSymbols.replace(mStoredSymbols.length() - 1, mStoredSymbols.length(), ")");
        stockSymbol = mStoredSymbols.toString();
      }
    } else if (params.getTag().equals("add")){
      isUpdate = false;
      // get symbol from params.getExtra and build query
      String stockInput = params.getExtras().getString("symbol");
      stockSymbol = "\"" + stockInput + "\")";
    }
    //getStockDetails(stockSymbol);
    int result = GcmNetworkManager.RESULT_FAILURE;
    StockClient stockClient = new StockClient();
    StockDetails stockDetails = stockClient.getStockQDetails(stockSymbol);
    if (stockDetails != null) {
      if(params.getTag().equals("add") &&
              stockDetails.getQuery().getResults().getQuote().get(0).getStockExchange()==null){
        result = GcmNetworkManager.RESULT_FAILURE;
      } else {
        result = GcmNetworkManager.RESULT_SUCCESS;
        ContentValues contentValues = new ContentValues();
        // update ISCURRENT to 0 (false) so new data is current
        if (isUpdate) {
          contentValues.put(QuoteColumns.ISCURRENT, 0);
          mContext.getContentResolver().update(QuoteProvider.Quotes.CONTENT_URI, contentValues,
                  null, null);
        }
        try {
          mContext.getContentResolver().applyBatch(QuoteProvider.AUTHORITY,
                  Utils.quoteJsonToContentVals(stockDetails));
          updateWidgets();
        } catch (RemoteException e) {
          e.printStackTrace();
        } catch (OperationApplicationException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }

  private void updateWidgets() {
    Log.d("STOCK TASK", "sending broadcast");
    // Setting the package ensures that only components in our app will receive the broadcast
    Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED)
            .setPackage(mContext.getPackageName());
    mContext.sendBroadcast(dataUpdatedIntent);
  }

}
