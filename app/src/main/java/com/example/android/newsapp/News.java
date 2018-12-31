package com.example.android.newsapp;

public class News {
    /**
     * Magnitude of the earthquake
     */ // the proceeding m just indicates a private variable
    private Double mMagnitude;
    /**
     * Location the earthquake occurred
     */
    private String mLocation;
    /**
     * URL for earthquake webpage
     */
    private String mUrl;
    /**
     * The date when the earthquake occurred
     */
    private long mtimeInMilliseconds;

    /**
     * Create a new Earthquake object.
     *
     * @param magnitude             is the magnitude of the earthquake
     * @param location              is the location where the earthquake occurred
     * @param url                   is the url for the earthquake info
     * @param timeInMilliseconds    is the date the earthquake occurred
     *
     */
    public News(Double magnitude, String location, String url, long timeInMilliseconds) {
        mMagnitude = magnitude;
        mLocation = location;
        mUrl = url;
        mtimeInMilliseconds = timeInMilliseconds;
    }

    /**
     * Get the earthquake magnitude.
     */
    public Double getMagnitude() {
        return mMagnitude;
    }

    /**
     * Get the earthquake location.
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Get the earthquake url.
     */
    public String getURL() {
        return mUrl;
    }

    /**
     * Get the date the earthquake occurred.
     */

    public long getTimeInMilliseconds() {
        return mtimeInMilliseconds;
    }
}

