package com.example.android.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Edwin on 8/31/2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {


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

        // Find the TextView in the book_list_item_item.xml layout with the ID book_title_text_view
        TextView ArticleTitleTextView = (TextView) listItemView.findViewById(R.id.article_title_text_view);

        // Find the TextView in the book_list_item.xml layout with the ID book_author_text_view
        TextView ArticleSectionTextView = (TextView) listItemView.findViewById(R.id.article_section_text_view);

        ArticleTitleTextView.setText(articleTitle);
        ArticleSectionTextView.setText(articleSection);

        return listItemView;
    }











} // End of ArticleAdapter class