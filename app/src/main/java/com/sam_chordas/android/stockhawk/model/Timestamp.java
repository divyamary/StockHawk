
package com.sam_chordas.android.stockhawk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Timestamp implements Parcelable{

    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("max")
    @Expose
    private Integer max;

    protected Timestamp(Parcel in) {
    }

    public static final Creator<Timestamp> CREATOR = new Creator<Timestamp>() {
        @Override
        public Timestamp createFromParcel(Parcel in) {
            return new Timestamp(in);
        }

        @Override
        public Timestamp[] newArray(int size) {
            return new Timestamp[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
