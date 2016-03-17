package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sam_chordas.android.stockhawk.R;

public class StockChartActivity extends AppCompatActivity {

    private static final String BUNDLE_STOCK_NAME="BUNDLE_STOCK_NAME";
    private static final String STOCK_DETAIL_FRAGMENT = "STOCK_DETAIL_FRAGMENT";
    public static final String ACTION_DATA_UPDATED =
            "com.sam_chordas.android.stockhawk.ACTION_DATA_UPDATED";
    private String stockSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StockChartFragment stockChartFragment;
        if (savedInstanceState == null) {
            // During initial setup, plug in the details fragment.
            stockChartFragment = new StockChartFragment();
            stockSymbol = getIntent().getStringExtra("symbol");
            stockChartFragment.setArguments(getIntent().getExtras());
        } else {
            stockChartFragment = (StockChartFragment)getSupportFragmentManager().findFragmentByTag(STOCK_DETAIL_FRAGMENT);
            stockSymbol = savedInstanceState.getString(BUNDLE_STOCK_NAME);
        }
        if(stockSymbol!=null) {
            getSupportActionBar().setTitle(stockSymbol);
        }
        getSupportFragmentManager().beginTransaction().replace(
                android.R.id.content, stockChartFragment, STOCK_DETAIL_FRAGMENT).commitAllowingStateLoss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_STOCK_NAME, stockSymbol);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        stockSymbol = savedInstanceState.getString(BUNDLE_STOCK_NAME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
