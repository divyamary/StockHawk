package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sam_chordas.android.stockhawk.R;

public class StockDetailsActivity extends AppCompatActivity {

    public static final String STOCK_DETAIL_FRAGMENT = "STOCK_DETAIL_FRAGMENT";
    private static final String BUNDLE_STOCK_NAME="BUNDLE_STOCK_NAME";
    private String stockSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StockDetailsFragment stockDetailsFragment;
        if (savedInstanceState == null) {
            stockDetailsFragment = new StockDetailsFragment();
            stockSymbol = getIntent().getStringExtra("symbol");
            stockDetailsFragment.setArguments(getIntent().getExtras());
        } else {
            stockDetailsFragment = (StockDetailsFragment) getSupportFragmentManager().findFragmentByTag(STOCK_DETAIL_FRAGMENT);
            stockSymbol = savedInstanceState.getString(BUNDLE_STOCK_NAME);
        }
        if(stockSymbol!=null) {
            //getSupportActionBar().setTitle(stockSymbol);
        }
        getSupportFragmentManager().beginTransaction().replace(
                android.R.id.content, stockDetailsFragment, STOCK_DETAIL_FRAGMENT).commitAllowingStateLoss();
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
