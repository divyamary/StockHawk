
package com.sam_chordas.android.stockhawk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cache {

    @SerializedName("execution-start-time")
    @Expose
    private String executionStartTime;
    @SerializedName("execution-stop-time")
    @Expose
    private String executionStopTime;
    @SerializedName("execution-time")
    @Expose
    private String executionTime;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("content")
    @Expose
    private String content;

    /**
     * @return The executionStartTime
     */
    public String getExecutionStartTime() {
        return executionStartTime;
    }

    /**
     * @param executionStartTime The execution-start-time
     */
    public void setExecutionStartTime(String executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    /**
     * @return The executionStopTime
     */
    public String getExecutionStopTime() {
        return executionStopTime;
    }

    /**
     * @param executionStopTime The execution-stop-time
     */
    public void setExecutionStopTime(String executionStopTime) {
        this.executionStopTime = executionStopTime;
    }

    /**
     * @return The executionTime
     */
    public String getExecutionTime() {
        return executionTime;
    }

    /**
     * @param executionTime The execution-time
     */
    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * @return The method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method The method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

}
