package com.sam_chordas.android.stockhawk.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StockDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            StockDetailsFragment stockChartFragment = new StockDetailsFragment();
            stockChartFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(
                    android.R.id.content, stockChartFragment).commitAllowingStateLoss();
        }
    }

}
