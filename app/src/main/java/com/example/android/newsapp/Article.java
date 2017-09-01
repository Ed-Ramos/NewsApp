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

    /** Web publication date the article */
    private String mWebPublicationDate;

    public Article(String WebTitle, String SectionName, String WebUrl, String WebPublicationDate) {
        mWebTitle = WebTitle;
        mSectionName = SectionName;
        mWebUrl = WebUrl;
        mWebPublicationDate = WebPublicationDate;
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

    public String getWebPublicationDate() {
        return mWebPublicationDate;
    }

}
