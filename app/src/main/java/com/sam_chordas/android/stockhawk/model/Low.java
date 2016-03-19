package com.sam_chordas.android.stockhawk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Low {

    @SerializedName("min")
    @Expose
    private Double min;
    @SerializedName("max")
    @Expose
    private Double max;

    /**
     * @return The min
     */
    public Double getMin() {
        return min;
    }

    /**
     * @param min The min
     */
    public void setMin(Double min) {
        this.min = min;
    }

    /**
     * @return The max
     */
    public Double getMax() {
        return max;
    }

    /**
     * @param max The max
     */
    public void setMax(Double max) {
        this.max = max;
    }

}
