package com.sam_chordas.android.stockhawk.ui;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.StockBus;
import com.sam_chordas.android.stockhawk.StockChartResultEvent;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.model.Series;
import com.sam_chordas.android.stockhawk.model.StockChart;
import com.sam_chordas.android.stockhawk.service.StockClient;
import com.squareup.otto.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


public class StockChartFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener ,
        OnChartGestureListener,
        OnChartValueSelectedListener {

    private static final String BUNDLE_CHART_RESPONSE = "BUNDLE_CHART_RESPONSE";
    private static final int CHART_LOADER = 1;
    private Cursor mCursor;
    private boolean mIsBusRegistered;
    @Bind(R.id.linechart)
    LineChart mChart;
    private int seriesSize;
    private List<String> listLabels;
    private String stockSymbol;
    private StockChart stockChart;
    private String range = "1d";
    private StockClient mStockClient;
    private String dateFormat = "h:mm";
    @Bind(R.id.button_1D)
    Button oneDayButton;
    @Bind(R.id.button_1W)
    Button oneWeekButton;
    @Bind(R.id.button_1M)
    Button oneMonthButton;
    @Bind(R.id.button_3M)
    Button threeMonthButton;
    @Bind(R.id.button_6M)
    Button sixMonthButton;
    @Bind(R.id.button_1Y)
    Button oneYearButton;
    @Bind(R.id.button_5Y)
    Button fiveYearsButton;
    @Bind(R.id.button_max)
    Button maxButton;
    @Bind(R.id.bid_price)
    TextView bidTextView;
    @Bind(R.id.text_open)
    TextView openTextView;
    @Bind(R.id.text_high)
    TextView highTextView;
    @Bind(R.id.text_low)
    TextView lowTextView;
    @Bind(R.id.text_vol)
    TextView volTextView;
    @Bind(R.id.text_eps)
    TextView epsTextView;
    @Bind(R.id.text_divyield)
    TextView divYieldTextView;
    @Bind(R.id.text_mktcap)
    TextView mktCapTextView;
    @Bind(R.id.text_yrhigh)
    TextView yrHighTextView;
    @Bind(R.id.text_yrlow)
    TextView yrLowTextView;
    @Bind(R.id.text_avgvol)
    TextView avgVolTextView;
    @Bind(R.id.text_pe)
    TextView peTextView;
    @Bind(R.id.text_delay)
    TextView delayTextView;
    private boolean mIsViewRestored;

    public StockChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_stock_chart, container, false);
        ButterKnife.bind(this, rootView);
        oneDayButton.setOnClickListener(this);
        oneWeekButton.setOnClickListener(this);
        oneMonthButton.setOnClickListener(this);
        threeMonthButton.setOnClickListener(this);
        sixMonthButton.setOnClickListener(this);
        oneYearButton.setOnClickListener(this);
        fiveYearsButton.setOnClickListener(this);
        maxButton.setOnClickListener(this);
        stockSymbol = getArguments().getString("symbol");
        mStockClient = new StockClient();

        if(savedInstanceState!=null){
            stockChart = savedInstanceState.getParcelable(BUNDLE_CHART_RESPONSE);
        } else {
            mStockClient.getStockChart(stockSymbol, range);
        }

        mChart.setDrawGridBackground(false);
        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("Loading...");
        // enable touch gestures
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);

        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);

        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setSpaceBetweenLabels(4);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setAvoidFirstLastClipping(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(CHART_LOADER, null, this);
        if(mIsViewRestored) {
            LinkedList<Double> closingValues = getStockClosingVals(stockChart);
            setData(seriesSize, closingValues);
        }
    }

    @Override
    public void onDestroy() {
        unregisterBus();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_CHART_RESPONSE, stockChart);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null){
            stockChart = savedInstanceState.getParcelable(BUNDLE_CHART_RESPONSE);
            mIsViewRestored = true;
        }
    }

    private void registerBus() {
        if (!mIsBusRegistered) {
            StockBus.getInstance().register(this);
            mIsBusRegistered = true;
        }
    }

    private void unregisterBus() {
        if (mIsBusRegistered) {
            StockBus.getInstance().unregister(this);
            mIsBusRegistered = false;
        }
    }

    @Subscribe
    public void onStockChartResult(StockChartResultEvent stockChartResultEvent) {
        mChart.clear();
        stockChart = stockChartResultEvent.getStockChart();
        LinkedList<Double> closingValues = getStockClosingVals(stockChart);
        setData(seriesSize, closingValues);
    }

    private void setData(int count, LinkedList<Double> range) {

        /*ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }*/

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < range.size(); i++) {
            Double val = range.get(i);
            yVals.add(new Entry(val.floatValue(), i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
        set1.setFillDrawable(drawable);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(listLabels, dataSets);

        // set data
        mChart.setData(data);
        mChart.setVisibleXRangeMaximum(seriesSize);
    }


    public LinkedList getStockClosingVals(StockChart stockChart) {
        LinkedList<Double> closingValues = new LinkedList<>();
        listLabels = new ArrayList<>();
        seriesSize = stockChart.getSeries().size();
        for (Series series : stockChart.getSeries()) {
            String date="";
            if (range.equals("1d") || range.equals("7d")){
                Long timeStamp = series.getTimestamp();
                Date time = new Date(timeStamp * 1000L);
                date = new SimpleDateFormat(dateFormat).format(time);
             } else {
                try {
                    Long dateStamp = series.getDateStamp();
                    Date time = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(String.valueOf(dateStamp));
                    date = new SimpleDateFormat(dateFormat).format(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            listLabels.add(date);
            closingValues.add(series.getClose());
        }
        return closingValues;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), QuoteProvider.Quotes.CONTENT_URI,
                null,
                QuoteColumns.SYMBOL + " = ?",
                new String[]{stockSymbol},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursor = data;
        if (mCursor != null) {
            mCursor.moveToFirst();
            bidTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.BIDPRICE)));
            openTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.OPEN)));
            highTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.DAYS_HIGH)));
            lowTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.DAYS_LOW)));
            volTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.VOLUME)));
            epsTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.EARNINGS_SHARE)));
            divYieldTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.DIVIDEND_YIELD)));
            mktCapTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.MARKET_CAP)));
            yrHighTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.YEAR_HIGH)));
            yrLowTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.YEAR_LOW)));
            avgVolTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.AVG_DAILY_VOL)));
            peTextView.setText(mCursor.getString(mCursor.getColumnIndex(QuoteColumns.PE_RATIO)));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.button_1D: {
                dateFormat = "h:mm";
                range = "1d";
                break;
            }
            case R.id.button_1W: {
                dateFormat = "MM-dd";
                range = "7d";
                break;
            }
            case R.id.button_1M: {
                dateFormat = "d MMM";
                range = "1m";
                break;
            }
            case R.id.button_3M: {
                dateFormat = "d MMM";
                range = "3m";
                break;
            }
            case R.id.button_6M: {
                dateFormat = "d MMM";
                range = "6m";
                break;
            }
            case R.id.button_1Y: {
                dateFormat = "d MMM";
                range = "1y";
                break;
            }
            case R.id.button_5Y: {
                dateFormat = "yyyy";
                range = "5y";
                break;
            }
            case R.id.button_max: {
                dateFormat = "yyyy";
                range = "max";
                break;
            }
            default: {
                dateFormat = "h:mm";
                range = "1d";
                break;
            }
        }
        mStockClient.getStockChart(stockSymbol, range);
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}
