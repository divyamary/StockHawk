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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
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
import com.sam_chordas.android.stockhawk.Utils;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.model.Series;
import com.sam_chordas.android.stockhawk.model.StockChart;
import com.sam_chordas.android.stockhawk.rest.ErrorBundle;
import com.sam_chordas.android.stockhawk.rest.ErrorResultEvent;
import com.sam_chordas.android.stockhawk.rest.StockBus;
import com.sam_chordas.android.stockhawk.rest.StockChartResultEvent;
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


public class StockDetailsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener,
        OnChartGestureListener, OnChartValueSelectedListener {

    private static final String BUNDLE_CHART_RESPONSE = "BUNDLE_CHART_RESPONSE";
    private static final String BUNDLE_RANGE_TYPE = "BUNDLE_RANGE_TYPE";
    private static final String BUNDLE_DATE_FORMAT = "BUNDLE_DATE_FORMAT";
    private static final String BUNDLE_STOCK_SYMBOL = "BUNDLE_STOCK_SYMBOL";
    private static final int DETAIL_LOADER = 1;
    @Bind(R.id.linechart)
    LineChart mChart;
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
    @Bind(R.id.text_prevclose)
    TextView prevCloseTextView;
    @Bind(R.id.name)
    TextView nameTextView;
    @Bind(R.id.change)
    TextView changeTextView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.text_toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.button_back)
    ImageButton backButton;
    private String LOG_TAG = StockDetailsFragment.class.getSimpleName();
    private boolean mIsBusRegistered;
    private int seriesSize;
    private List<String> listLabels;
    private String stockSymbol;
    private StockChart stockChart;
    private String range = "1d";
    private StockClient mStockClient;
    private String dateFormat = "h:mm";
    private boolean mIsViewRestored;

    public StockDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "StockDetailsFragment");
        super.onCreate(savedInstanceState);
        registerBus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stock_chart, container, false);
        ButterKnife.bind(this, rootView);
        stockSymbol = getArguments().getString("symbol");
        mStockClient = new StockClient(getContext());
        if (savedInstanceState != null) {
            stockChart = savedInstanceState.getParcelable(BUNDLE_CHART_RESPONSE);
            range = savedInstanceState.getString(BUNDLE_RANGE_TYPE);
            retainButtonTextColor(range);
            dateFormat = savedInstanceState.getString(BUNDLE_DATE_FORMAT);
            stockSymbol = savedInstanceState.getString(BUNDLE_STOCK_SYMBOL);
        } else {
            mStockClient.getStockChart(stockSymbol, range);
        }
        toolbarTitle.setText(stockSymbol);
        oneDayButton.setOnClickListener(this);
        oneWeekButton.setOnClickListener(this);
        oneMonthButton.setOnClickListener(this);
        threeMonthButton.setOnClickListener(this);
        sixMonthButton.setOnClickListener(this);
        oneYearButton.setOnClickListener(this);
        fiveYearsButton.setOnClickListener(this);
        maxButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        setUpChart();
        return rootView;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            stockChart = savedInstanceState.getParcelable(BUNDLE_CHART_RESPONSE);
            range = savedInstanceState.getString(BUNDLE_RANGE_TYPE);
            dateFormat = savedInstanceState.getString(BUNDLE_DATE_FORMAT);
            stockSymbol = savedInstanceState.getString(BUNDLE_STOCK_SYMBOL);
            mIsViewRestored = true;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        if (mIsViewRestored && Utils.isConnected(getContext())) {
            LinkedList<Double> closingValues = getStockClosingVals(stockChart);
            setData(closingValues);
        }
        if (!Utils.isConnected(getContext())) {
            mChart.setNoDataText(getResources().getString(R.string.chart_fetch_error));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_CHART_RESPONSE, stockChart);
        outState.putString(BUNDLE_RANGE_TYPE, range);
        outState.putString(BUNDLE_DATE_FORMAT, dateFormat);
        outState.putString(BUNDLE_STOCK_SYMBOL, stockSymbol);
    }

    @Override
    public void onDestroy() {
        unregisterBus();
        super.onDestroy();
    }

    private void retainButtonTextColor(String range) {
        int viewId;
        switch (range) {
            case "1d":
                viewId = R.id.button_1D;
                break;
            case "7d":
                viewId = R.id.button_1W;
                break;
            case "1m":
                viewId = R.id.button_1M;
                break;
            case "3m":
                viewId = R.id.button_3M;
                break;
            case "6m":
                viewId = R.id.button_6M;
                break;
            case "1y":
                viewId = R.id.button_1Y;
                break;
            case "5y":
                viewId = R.id.button_5Y;
                break;
            case "max":
                viewId = R.id.button_max;
                break;
            default:
                viewId = R.id.button_1D;
                break;
        }
        changeButtonTextColor(viewId);
    }

    private void setUpChart() {
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        mChart.setNoDataText("Loading chart...");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setExtraOffsets(5f, 20f, 5f, 20f);
        CustomMarkerView customMarkerView = new CustomMarkerView(getContext(), R.layout.custom_marker_view);
        mChart.setMarkerView(customMarkerView);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setSpaceBetweenLabels(4);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawLabels(true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setTextColor(Color.WHITE);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setTextColor(Color.WHITE);
        mChart.getAxisRight().setEnabled(false);
        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        mChart.getLegend().setEnabled(false);
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
        setData(closingValues);
    }

    private void setData(LinkedList<Double> range) {
        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < range.size(); i++) {
            Double val = range.get(i);
            yVals.add(new Entry(val.floatValue(), i));
        }
        LineDataSet lineDataSet = new LineDataSet(yVals, "");
        lineDataSet.setColor(Color.WHITE);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(9f);
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_blue);
        lineDataSet.setFillDrawable(drawable);
        lineDataSet.setDrawFilled(true);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet); // add the datasets
        // create a data object with the datasets
        LineData data = new LineData(listLabels, dataSets);
        data.setValueTextSize(9f);
        data.setDrawValues(false);
        // set data
        mChart.setData(data);
        mChart.setVisibleXRangeMaximum(seriesSize);
    }


    private LinkedList<Double> getStockClosingVals(StockChart stockChart) {
        LinkedList<Double> closingValues = new LinkedList<>();
        listLabels = new ArrayList<>();
        seriesSize = stockChart.getSeries().size();
        Log.d(LOG_TAG, "SeriesSize:" + seriesSize);
        for (Series series : stockChart.getSeries()) {
            String date = "";
            if (range.equals("1d") || range.equals("7d")) {
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
                QuoteColumns.SYMBOL + " = ? AND " + QuoteColumns.ISCURRENT + "=?",
                new String[]{stockSymbol, "1"},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == DETAIL_LOADER) {
            Cursor qCursor = data;
            if (qCursor != null && qCursor.moveToFirst()) {
                nameTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.NAME)));
                bidTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.BIDPRICE)));
                openTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.OPEN)));
                highTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.DAYS_HIGH)));
                lowTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.DAYS_LOW)));
                volTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.VOLUME)));
                epsTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.EARNINGS_SHARE)));
                divYieldTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.DIVIDEND_YIELD)));
                mktCapTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.MARKET_CAP)));
                yrHighTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.YEAR_HIGH)));
                yrLowTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.YEAR_LOW)));
                avgVolTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.AVG_DAILY_VOL)));
                peTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.PE_RATIO)));
                prevCloseTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.PREV_CLOSE)));
                if (qCursor.getInt(qCursor.getColumnIndex(QuoteColumns.ISUP)) == 1) {
                    changeTextView.setTextColor(getResources().getColor(R.color.material_green_700));
                } else {
                    changeTextView.setTextColor(getResources().getColor(R.color.material_red_700));
                }
                if (Utils.showPercent) {
                    changeTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.PERCENT_CHANGE)));
                } else {
                    changeTextView.setText(qCursor.getString(qCursor.getColumnIndex(QuoteColumns.CHANGE)));
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        changeButtonTextColor(viewId);
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
            case R.id.button_back: {
                getActivity().finish();
                break;
            }
            default: {
                dateFormat = "h:mm";
                range = "1d";
                break;
            }
        }
        if (Utils.isConnected(getContext())) {
            mStockClient.getStockChart(stockSymbol, range);
        } else {
            mChart.clear();
            mChart.setNoDataText(getResources().getString(R.string.chart_fetch_error));
        }
    }

    private void changeButtonTextColor(int viewId) {
        switch (viewId) {
            case R.id.button_1D: {
                oneDayButton.setTextColor(getResources().getColor(R.color.material_blue_700));
                oneWeekButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                threeMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                sixMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneYearButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                fiveYearsButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                maxButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                break;
            }
            case R.id.button_1W: {
                oneDayButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneWeekButton.setTextColor(getResources().getColor(R.color.material_blue_700));
                oneMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                threeMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                sixMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneYearButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                fiveYearsButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                maxButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                break;
            }
            case R.id.button_1M: {
                oneDayButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneWeekButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneMonthButton.setTextColor(getResources().getColor(R.color.material_blue_700));
                threeMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                sixMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneYearButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                fiveYearsButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                maxButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                break;
            }
            case R.id.button_3M: {
                oneDayButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneWeekButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                threeMonthButton.setTextColor(getResources().getColor(R.color.material_blue_700));
                sixMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneYearButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                fiveYearsButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                maxButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                break;
            }
            case R.id.button_6M: {
                oneDayButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneWeekButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                threeMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                sixMonthButton.setTextColor(getResources().getColor(R.color.material_blue_700));
                oneYearButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                fiveYearsButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                maxButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                break;
            }
            case R.id.button_1Y: {
                oneDayButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneWeekButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                threeMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                sixMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneYearButton.setTextColor(getResources().getColor(R.color.material_blue_700));
                fiveYearsButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                maxButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                break;
            }
            case R.id.button_5Y: {
                oneDayButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneWeekButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                threeMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                sixMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneYearButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                fiveYearsButton.setTextColor(getResources().getColor(R.color.material_blue_700));
                maxButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                break;
            }
            case R.id.button_max: {
                oneDayButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneWeekButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                threeMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                sixMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneYearButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                fiveYearsButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                maxButton.setTextColor(getResources().getColor(R.color.material_blue_700));
                break;
            }
            default: {
                oneDayButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneWeekButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                threeMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                sixMonthButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                oneYearButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                fiveYearsButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                maxButton.setTextColor(getResources().getColor(R.color.material_gray_900));
                break;
            }

        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null);
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

    @Subscribe
    public void onRetrofitFailure(ErrorResultEvent event) {
        mChart.setNoDataText("");
        ErrorBundle errorBundle = event.getErrorBundle();
        String endpoint = event.getEndpoint();
        if (errorBundle != null && errorBundle.getAppMessage() != null
                && endpoint != null && endpoint.equals("ChartAPI")) {
            if (errorBundle.getAppMessage().equals("Unknown exception")) {
                mChart.setNoDataText(getResources().getString(R.string.chart_fetch_error));
            } else if (errorBundle.getAppMessage().equals("Socket timeout")) {
                mChart.setNoDataText(getResources().getString(R.string.chart_timeout_error));
            }
        }
    }
}
