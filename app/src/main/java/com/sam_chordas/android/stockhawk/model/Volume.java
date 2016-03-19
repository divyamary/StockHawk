package com.sam_chordas.android.stockhawk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Volume {

    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("max")
    @Expose
    private Integer max;

    /**
     * @return The min
     */
    public Integer getMin() {
        return min;
    }

    /**
     * @param min The min
     */
    public void setMin(Integer min) {
        this.min = min;
    }

    /**
     * @return The max
     */
    public Integer getMax() {
        return max;
    }

    /**
     * @param max The max
     */
    public void setMax(Integer max) {
        this.max = max;
    }

}
