package com.example.android.newsapp;

/**
 * Created by Edwin on 8/31/2017.
 */

public class Article {

    /** value that represents the article title */
    private String mWebTitle;

    /** value that represents the article section name */
    private String mSectionName;

    /** Website URL of the article */
    private String mWebUrl;

    public Article(String WebTitle, String SectionName, String WebUrl) {
        mWebTitle = WebTitle;
        mSectionName = SectionName;
        mWebUrl = WebUrl;
    }


    public String getWebTitle() {
        return mWebTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getWebUrl() {
        return mWebUrl;
    }
}
