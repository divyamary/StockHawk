package com.sam_chordas.android.stockhawk.rest;

import android.content.ContentProviderOperation;

import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;
import com.sam_chordas.android.stockhawk.model.Quote;
import com.sam_chordas.android.stockhawk.model.StockDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam_chordas on 10/8/15.
 */
public class Utils {

    public static boolean showPercent = true;
    private static String LOG_TAG = Utils.class.getSimpleName();

    public static ArrayList quoteJsonToContentVals(StockDetails stockDetails) {
        int count = stockDetails.getQuery().getCount();
        List<Quote> quotes = stockDetails.getQuery().getResults().getQuote();
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>();
        if (count == 1) {
            batchOperations.add(buildBatchOperation(quotes.get(0)));
        } else {
            if (quotes != null && quotes.size() != 0) {
                for (Quote quote : quotes) {
                    batchOperations.add(buildBatchOperation(quote));
                }
            }
        }
        return batchOperations;
    }


    public static String truncateBidPrice(String bidPrice) {
        bidPrice = String.format("%.2f", Float.parseFloat(bidPrice));
        return bidPrice;
    }

    public static String truncateChange(String change, boolean isPercentChange) {
        String weight = change.substring(0, 1);
        String ampersand = "";
        if (isPercentChange) {
            ampersand = change.substring(change.length() - 1, change.length());
            change = change.substring(0, change.length() - 1);
        }
        change = change.substring(1, change.length());
        double round = (double) Math.round(Double.parseDouble(change) * 100) / 100;
        change = String.format("%.2f", round);
        StringBuffer changeBuffer = new StringBuffer(change);
        changeBuffer.insert(0, weight);
        changeBuffer.append(ampersand);
        change = changeBuffer.toString();
        return change;
    }

    public static ContentProviderOperation buildBatchOperation(Quote quote) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                QuoteProvider.Quotes.CONTENT_URI);
        String change = quote.getChange();
        if (change != null) {
            builder.withValue(QuoteColumns.CHANGE, truncateChange(change, false));
        } else {
            builder.withValue(QuoteColumns.CHANGE, "-");
        }
        builder.withValue(QuoteColumns.ISCURRENT, 1);
        if (change.charAt(0) == '-') {
            builder.withValue(QuoteColumns.ISUP, 0);
        } else {
            builder.withValue(QuoteColumns.ISUP, 1);
        }
        builder.withValue(QuoteColumns.SYMBOL, quote.getSymbol());
        if (quote.getBid() != null) {
            builder.withValue(QuoteColumns.BIDPRICE, truncateBidPrice(quote.getBid()));
        } else {
            builder.withValue(QuoteColumns.BIDPRICE, "-");
        }
        if (quote.getChangeinPercent() != null) {
            builder.withValue(QuoteColumns.PERCENT_CHANGE, truncateChange(
                    quote.getChangeinPercent(), true));
        } else {
            builder.withValue(QuoteColumns.PERCENT_CHANGE, "-");
        }
        if (quote.getAverageDailyVolume() != null) {
            builder.withValue(QuoteColumns.AVG_DAILY_VOL, quote.getAverageDailyVolume());
        } else {
            builder.withValue(QuoteColumns.AVG_DAILY_VOL, "-");
        }
        if (quote.getDaysHigh() != null) {
            builder.withValue(QuoteColumns.DAYS_HIGH, quote.getDaysHigh());
        } else {
            builder.withValue(QuoteColumns.DAYS_HIGH, "-");
        }
        if (quote.getDaysLow() != null) {
            builder.withValue(QuoteColumns.DAYS_LOW, quote.getDaysLow());
        } else {
            builder.withValue(QuoteColumns.DAYS_LOW, "-");
        }
        if (quote.getYearHigh() != null) {
            builder.withValue(QuoteColumns.YEAR_HIGH, quote.getYearHigh());
        } else {
            builder.withValue(QuoteColumns.YEAR_HIGH, "-");
        }
        if (quote.getYearLow() != null) {
            builder.withValue(QuoteColumns.YEAR_LOW, quote.getYearLow());
        } else {
            builder.withValue(QuoteColumns.YEAR_LOW, "-");
        }
        if (quote.getMarketCapitalization() != null) {
            builder.withValue(QuoteColumns.MARKET_CAP, quote.getMarketCapitalization());
        } else {
            builder.withValue(QuoteColumns.MARKET_CAP, "-");
        }
        if (quote.getEarningsShare() != null) {
            builder.withValue(QuoteColumns.EARNINGS_SHARE, quote.getEarningsShare());
        } else {
            builder.withValue(QuoteColumns.EARNINGS_SHARE, "-");
        }
        if (quote.getDividendYield() != null) {
            builder.withValue(QuoteColumns.DIVIDEND_YIELD, quote.getDividendYield());
        } else {
            builder.withValue(QuoteColumns.DIVIDEND_YIELD, "-");
        }
        if (quote.getVolume() != null) {
            builder.withValue(QuoteColumns.VOLUME, quote.getVolume());
        } else {
            builder.withValue(QuoteColumns.VOLUME, "-");
        }
        if (quote.getPERatio() != null) {
            builder.withValue(QuoteColumns.PE_RATIO, quote.getPERatio());
        } else {
            builder.withValue(QuoteColumns.PE_RATIO, "-");
        }
        if (quote.getPreviousClose() != null) {
            builder.withValue(QuoteColumns.PREV_CLOSE, quote.getPreviousClose());
        } else {
            builder.withValue(QuoteColumns.PREV_CLOSE, "-");
        }
        return builder.build();
    }
}
