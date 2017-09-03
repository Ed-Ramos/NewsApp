package com.example.android.newsapp;

public class Article {

    /**
     * value that represents the article title
     */
    private String mWebTitle;

    /**
     * value that represents the article section name
     */
    private String mSectionName;

    /**
     * Website URL of the article
     */
    private String mWebUrl;

    /**
     * Web publication date the article
     */
    private String mWebPublicationDate;

    /**
     * Web author of the article
     */
    private String mWebAuthor;


    public Article(String WebTitle, String SectionName, String WebUrl, String WebPublicationDate, String WebAuthor) {
        mWebTitle = WebTitle;
        mSectionName = SectionName;
        mWebUrl = WebUrl;
        mWebPublicationDate = WebPublicationDate;
        mWebAuthor = WebAuthor;
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

    public String getWebAuthor() {
        return mWebAuthor;
    }

}
