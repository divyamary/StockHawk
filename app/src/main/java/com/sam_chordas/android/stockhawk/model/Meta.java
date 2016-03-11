
package com.sam_chordas.android.stockhawk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("ticker")
    @Expose
    private String ticker;
    @SerializedName("Company-Name")
    @Expose
    private String CompanyName;
    @SerializedName("Exchange-Name")
    @Expose
    private String ExchangeName;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("gmtoffset")
    @Expose
    private Integer gmtoffset;
    @SerializedName("previous_close")
    @Expose
    private Double previousClose;

    /**
     * @return The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return The ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * @param ticker The ticker
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /**
     * @return The CompanyName
     */
    public String getCompanyName() {
        return CompanyName;
    }

    /**
     * @param CompanyName The Company-Name
     */
    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }

    /**
     * @return The ExchangeName
     */
    public String getExchangeName() {
        return ExchangeName;
    }

    /**
     * @param ExchangeName The Exchange-Name
     */
    public void setExchangeName(String ExchangeName) {
        this.ExchangeName = ExchangeName;
    }

    /**
     * @return The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return The timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * @param timezone The timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /**
     * @return The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return The gmtoffset
     */
    public Integer getGmtoffset() {
        return gmtoffset;
    }

    /**
     * @param gmtoffset The gmtoffset
     */
    public void setGmtoffset(Integer gmtoffset) {
        this.gmtoffset = gmtoffset;
    }

    /**
     * @return The previousClose
     */
    public Double getPreviousClose() {
        return previousClose;
    }

    /**
     * @param previousClose The previous_close
     */
    public void setPreviousClose(Double previousClose) {
        this.previousClose = previousClose;
    }

}
