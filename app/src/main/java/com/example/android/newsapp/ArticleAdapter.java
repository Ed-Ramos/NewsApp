package com.example.android.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Edwin on 8/31/2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Activity context, ArrayList<Article> articles) {
        super(context, 0, articles);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.article_list_item, parent, false);

        }

        // Get the {@link Book} object located at this position in the list
        Article currentArticle = getItem(position);

        String articleTitle = currentArticle.getWebTitle();
        String articleSection = currentArticle.getSectionName();
        String articleDate = currentArticle.getWebPublicationDate();
        String articleAuthor = currentArticle.getWebAuthor();

        // Find the TextView in the book_list_item_item.xml layout with the ID book_title_text_view
        TextView ArticleTitleTextView = (TextView) listItemView.findViewById(R.id.article_title_text_view);

        // Find the TextView in the article_list_item.xml layout with the ID article_section_text_view
        TextView ArticleSectionTextView = (TextView) listItemView.findViewById(R.id.article_section_text_view);

        // Find the TextView in the article_list_item.xml layout with the ID article_date_text_view
        TextView ArticleDateTextView = (TextView) listItemView.findViewById(R.id.article_date_text_view);

        // Find the TextView in the article_list_item.xml layout with the ID article_author_text_view
        TextView ArticleAuthorTextView = (TextView) listItemView.findViewById(R.id.article_author_text_view);



        ArticleTitleTextView.setText(articleTitle);
        ArticleSectionTextView.setText("Found under section: " + articleSection);
        ArticleDateTextView.setText("Published on " + articleDate);
        ArticleAuthorTextView.setText("By: " + articleAuthor);

        return listItemView;
    }











} // End of ArticleAdapter class
