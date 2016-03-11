
package com.sam_chordas.android.stockhawk.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Diagnostics {

    @SerializedName("url")
    @Expose
    private List<Url> url = new ArrayList<Url>();
    @SerializedName("publiclyCallable")
    @Expose
    private String publiclyCallable;
    @SerializedName("cache")
    @Expose
    private Cache cache;
    @SerializedName("query")
    @Expose
    private Query_ query;
    @SerializedName("javascript")
    @Expose
    private Javascript javascript;
    @SerializedName("user-time")
    @Expose
    private String userTime;
    @SerializedName("service-time")
    @Expose
    private String serviceTime;
    @SerializedName("build-version")
    @Expose
    private String buildVersion;

    /**
     * @return The url
     */
    public List<Url> getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(List<Url> url) {
        this.url = url;
    }

    /**
     * @return The publiclyCallable
     */
    public String getPubliclyCallable() {
        return publiclyCallable;
    }

    /**
     * @param publiclyCallable The publiclyCallable
     */
    public void setPubliclyCallable(String publiclyCallable) {
        this.publiclyCallable = publiclyCallable;
    }

    /**
     * @return The cache
     */
    public Cache getCache() {
        return cache;
    }

    /**
     * @param cache The cache
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    /**
     * @return The query
     */
    public Query_ getQuery() {
        return query;
    }

    /**
     * @param query The query
     */
    public void setQuery(Query_ query) {
        this.query = query;
    }

    /**
     * @return The javascript
     */
    public Javascript getJavascript() {
        return javascript;
    }

    /**
     * @param javascript The javascript
     */
    public void setJavascript(Javascript javascript) {
        this.javascript = javascript;
    }

    /**
     * @return The userTime
     */
    public String getUserTime() {
        return userTime;
    }

    /**
     * @param userTime The user-time
     */
    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    /**
     * @return The serviceTime
     */
    public String getServiceTime() {
        return serviceTime;
    }

    /**
     * @param serviceTime The service-time
     */
    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    /**
     * @return The buildVersion
     */
    public String getBuildVersion() {
        return buildVersion;
    }

    /**
     * @param buildVersion The build-version
     */
    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

}
