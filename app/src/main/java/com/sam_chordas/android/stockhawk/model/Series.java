
package com.sam_chordas.android.stockhawk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Series {

    @SerializedName("Timestamp")
    @Expose
    private Long Timestamp;
    @SerializedName("close")
    @Expose
    private Double close;
    @SerializedName("high")
    @Expose
    private Double high;
    @SerializedName("low")
    @Expose
    private Double low;
    @SerializedName("open")
    @Expose
    private Double open;
    @SerializedName("volume")
    @Expose
    private Integer volume;

    /**
     * @return The Timestamp
     */
    public Long getTimestamp() {
        return Timestamp;
    }

    /**
     * @param Timestamp The Timestamp
     */
    public void setTimestamp(Long Timestamp) {
        this.Timestamp = Timestamp;
    }

    /**
     * @return The close
     */
    public Double getClose() {
        return close;
    }

    /**
     * @param close The close
     */
    public void setClose(Double close) {
        this.close = close;
    }

    /**
     * @return The high
     */
    public Double getHigh() {
        return high;
    }

    /**
     * @param high The high
     */
    public void setHigh(Double high) {
        this.high = high;
    }

    /**
     * @return The low
     */
    public Double getLow() {
        return low;
    }

    /**
     * @param low The low
     */
    public void setLow(Double low) {
        this.low = low;
    }

    /**
     * @return The open
     */
    public Double getOpen() {
        return open;
    }

    /**
     * @param open The open
     */
    public void setOpen(Double open) {
        this.open = open;
    }

    /**
     * @return The volume
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     * @param volume The volume
     */
    public void setVolume(Integer volume) {
        this.volume = volume;
    }

}
