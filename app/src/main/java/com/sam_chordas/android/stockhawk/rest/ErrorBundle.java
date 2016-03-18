package com.sam_chordas.android.stockhawk.rest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.net.SocketTimeoutException;

/**
 * @author Tom Koptel
 */
public final class ErrorBundle {
    private final String appMessage;
    private final String rawMessage;

    private ErrorBundle(String message, String reason) {
        this.appMessage = message;
        this.rawMessage = reason;
    }

    @NonNull
    public static ErrorBundle adapt(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            String reason = null;
            if (throwable.getCause() != null) {
                reason = throwable.getCause().getMessage();
            }
            if (reason == null) {
                reason = "Connection timed out";
            }
            return new ErrorBundle("Socket timeout", reason);
        }
        return new ErrorBundle("Unknown exception", throwable.getMessage());
    }

    @NonNull
    public String getAppMessage() {
        return appMessage;
    }

    @Nullable
    public String getRawMessage() {
        return rawMessage;
    }
}
