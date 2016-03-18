package com.sam_chordas.android.stockhawk.rest;

/**
 * Created by divyamary on 10-02-2016.
 */
public class ErrorResultEvent {

    private String message;
    private String endpoint;

    private ErrorBundle mErrorBundle;


    public ErrorResultEvent(ErrorBundle errorBundle, String endpoint) {
        this.mErrorBundle = errorBundle;
        this.endpoint = endpoint;
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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
