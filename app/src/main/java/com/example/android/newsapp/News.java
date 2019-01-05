package com.example.android.newsapp;

import java.util.ArrayList;

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
    private String mNewsTitle;
    /**
     * Section where article resides
     */
    private String mSectionName;
    /**
     * Author/s of the article
     */
    private ArrayList<String> mAuthors;


    /**
     * Create a new News object.
     *
     * @param weburl             Web URL of the news article
     * @param webpublicationdate Publication date of the article
     * @param newstitle          News article title
     * @param sectionname        Section where article resides
     * @param authors           Name of the author/s for the article
     *
     */
    public News(String weburl, String webpublicationdate, String newstitle, String sectionname, ArrayList<String> authors) {
        mWebUrl = weburl;
        mWebPublicationDate = webpublicationdate;
        mNewsTitle = newstitle;
        mSectionName = sectionname;
        mAuthors = authors;
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
    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }

    /**
     * Get news article title.
     */
    public String getNewsTitle() {
        return mNewsTitle;
    }

    /**
     * Get section where article resides.
     */
    public String getSectionName() {
        return mSectionName;
    }
    /**
     * Get author/s for the article.
     */
    public ArrayList<String> getAuthors() {
        return mAuthors;
    }
}