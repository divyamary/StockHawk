
package com.sam_chordas.android.stockhawk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StockResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("Timestamp")
    @Expose
    private Timestamp Timestamp;
    @SerializedName("labels")
    @Expose
    private List<Long> labels = new ArrayList<Long>();
    @SerializedName("ranges")
    @Expose
    private Ranges ranges;
    @SerializedName("series")
    @Expose
    private List<Series> series = new ArrayList<Series>();

    /**
     * @return The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * @param meta The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     * @return The Timestamp
     */
    public Timestamp getTimestamp() {
        return Timestamp;
    }

    /**
     * @param Timestamp The Timestamp
     */
    public void setTimestamp(Timestamp Timestamp) {
        this.Timestamp = Timestamp;
    }

    /**
     * @return The labels
     */
    public List<Long> getLabels() {
        return labels;
    }

    /**
     * @param labels The labels
     */
    public void setLabels(List<Long> labels) {
        this.labels = labels;
    }

    /**
     * @return The ranges
     */
    public Ranges getRanges() {
        return ranges;
    }

    /**
     * @param ranges The ranges
     */
    public void setRanges(Ranges ranges) {
        this.ranges = ranges;
    }

    /**
     * @return The series
     */
    public List<Series> getSeries() {
        return series;
    }

    /**
     * @param series The series
     */
    public void setSeries(List<Series> series) {
        this.series = series;
    }

}
