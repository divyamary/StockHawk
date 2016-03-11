
package com.sam_chordas.android.stockhawk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Ranges {

    @SerializedName("close")
    @Expose
    private Close close;
    @SerializedName("high")
    @Expose
    private High high;
    @SerializedName("low")
    @Expose
    private Low low;
    @SerializedName("open")
    @Expose
    private Open open;
    @SerializedName("volume")
    @Expose
    private Volume volume;

    /**
     * @return The close
     */
    public Close getClose() {
        return close;
    }

    /**
     * @param close The close
     */
    public void setClose(Close close) {
        this.close = close;
    }

    /**
     * @return The high
     */
    public High getHigh() {
        return high;
    }

    /**
     * @param high The high
     */
    public void setHigh(High high) {
        this.high = high;
    }

    /**
     * @return The low
     */
    public Low getLow() {
        return low;
    }

    /**
     * @param low The low
     */
    public void setLow(Low low) {
        this.low = low;
    }

    /**
     * @return The open
     */
    public Open getOpen() {
        return open;
    }

    /**
     * @param open The open
     */
    public void setOpen(Open open) {
        this.open = open;
    }

    /**
     * @return The volume
     */
    public Volume getVolume() {
        return volume;
    }

    /**
     * @param volume The volume
     */
    public void setVolume(Volume volume) {
        this.volume = volume;
    }

}
