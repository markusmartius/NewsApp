package com.example.android.newsapp;

import android.app.Activity;
        import android.app.LoaderManager;
        import android.content.Loader;
        import android.os.Bundle;
        import android.support.v4.content.ContextCompat;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;
        import java.text.DecimalFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import android.graphics.drawable.GradientDrawable;

public class NewsAdapter extends ArrayAdapter<News> implements LoaderManager.LoaderCallbacks<News> {

    private String titleString;
    private String sectionString;
    private String webpublicationdate;

    // New constructor
    public NewsAdapter(Activity context, ArrayList<News> nameForArrayOfNewsObjects) {
        // the zero means we're initially not passing a view as it will be passed later???
        super(context, 0, nameForArrayOfNewsObjects);
        // Set colour of list item
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        News currentNews = getItem(position);

        // get title text from currentNews and slip it at the desired point
        titleString = currentNews.getNewsTitle();

        // get title text from currentNews and slip it at the desired point
        sectionString = currentNews.getSectionName();

        // get title text from currentNews and slip it at the desired point
        webpublicationdate = currentNews.getWebPublicationDate();

        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView newstitleTextView = (TextView) listItemView.findViewById(R.id.newstitle);
        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView pubdateTextView = (TextView) listItemView.findViewById(R.id.pubdate);
        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView articletypeTextView = (TextView) listItemView.findViewById(R.id.articletype);

        // add the news title text to the text view
        newstitleTextView.setText(titleString);
        // set the publication date text to the text view
        pubdateTextView.setText(webpublicationdate);
        // set the article type text to the text view
        articletypeTextView.setText(sectionString);

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    @Override
    public Loader<News> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<News> loader, News data) {

    }

    @Override
    public void onLoaderReset(Loader<News> loader) {

    }
}