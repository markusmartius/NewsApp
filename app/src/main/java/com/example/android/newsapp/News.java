package com.example.android.newsapp;

public class News {
    /**
     * Web URL of the news article
     */
    private String mWebUrl;
    /**
     * Publication date of the article
     */
    private String mWebPublicationDate;
    /**
     * News article title
     */
    private String mWebTitle;
    /**
     * Section where article resides
     */
    private String mSectionName;

    /**
     * Create a new Earthquake object.
     *
     * @param weburl             Web URL of the news article
     * @param webpublicationdate Publication date of the article
     * @param webtitle           News article title
     * @param sectionname        Section where article resides
     *
     */
    public News(String weburl, String webpublicationdate, String webtitle, String sectionname) {
        mWebUrl = weburl;
        mWebPublicationDate = webpublicationdate;
        mWebTitle = webtitle;
        mSectionName = sectionname;
    }

    /**
     * Get Web URL of the news article.
     */
    public String getWebUrl() {
        return mWebUrl;
    }

    /**
     * Get publication date of the article.
     */
    public String getWebPublicationDateLocation() {
        return mWebPublicationDate;
    }

    /**
     * Get news article title.
     */
    public String getWebTitleURL() {
        return mWebTitle;
    }

    /**
     * Get section where article resides.
     */

    public String getSectionName() {
        return mSectionName;
    }
}