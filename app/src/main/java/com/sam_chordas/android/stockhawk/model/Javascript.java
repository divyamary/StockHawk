
package com.sam_chordas.android.stockhawk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Javascript {

    @SerializedName("execution-start-time")
    @Expose
    private String executionStartTime;
    @SerializedName("execution-stop-time")
    @Expose
    private String executionStopTime;
    @SerializedName("execution-time")
    @Expose
    private String executionTime;
    @SerializedName("instructions-used")
    @Expose
    private String instructionsUsed;
    @SerializedName("table-name")
    @Expose
    private String tableName;

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
     * @return The instructionsUsed
     */
    public String getInstructionsUsed() {
        return instructionsUsed;
    }

    /**
     * @param instructionsUsed The instructions-used
     */
    public void setInstructionsUsed(String instructionsUsed) {
        this.instructionsUsed = instructionsUsed;
    }

    /**
     * @return The tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName The table-name
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
