package com.sam_chordas.android.stockhawk.rest;

/**
 * Created by divyamary on 10-02-2016.
 */
public class ErrorResultEvent {

    private String message;

    private ErrorBundle mErrorBundle;


    public ErrorResultEvent(ErrorBundle errorBundle) {
        this.mErrorBundle = errorBundle;
    }

    public ErrorResultEvent(String message) {
        this.message = message;
    }

    public ErrorBundle getErrorBundle() {
        return mErrorBundle;
    }

    public void setErrorBundle(ErrorBundle errorBundle) {
        mErrorBundle = errorBundle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
