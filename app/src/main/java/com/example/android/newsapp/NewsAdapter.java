package com.example.android.newsapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdapter extends ArrayAdapter<News> implements LoaderManager.LoaderCallbacks<News> {

    private String sectionString;

    // New constructor
    public NewsAdapter(Activity context, ArrayList<News> nameForArrayOfNewsObjects) {
        // the zero means we're initially not passing a view as it will be passed later.
        super(context, 0, nameForArrayOfNewsObjects);
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
        String titleString = currentNews.getNewsTitle();

        // get title text from currentNews and slip it at the desired point
        sectionString = currentNews.getSectionName();

        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView newstitleTextView = listItemView.findViewById(R.id.newstitle);

        Date modifiedDate = new Date();
        // Create a new Date object from the date in the JSON
        try {
            modifiedDate = dateCreator(currentNews.getWebPublicationDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView pubdateTextView = listItemView.findViewById(R.id.pubdate);
        // Format the date string (i.e. "Jan 2, 1972")
        String formattedDate = formatDate(modifiedDate);
        // set the publication date text to the text view
        pubdateTextView.setText(formattedDate);

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView timeTextView = listItemView.findViewById(R.id.pubtime);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(modifiedDate);
        // Get the News date from the current News object and
        // set this text on the name TextView
        timeTextView.setText(formattedTime);

        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView articletypeTextView = listItemView.findViewById(R.id.articletype);

        // add the news title text to the text view
        newstitleTextView.setText(titleString);
        // set the article type text to the text view
        articletypeTextView.setText(sectionString);

        // Return the whole list item layout
        // so that it can be shown in the ListView
        return listItemView;
    }

    /**
     * Convert date string from JSON into simpledateformat
     */
    private Date dateCreator (String createDate) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return parser.parse(createDate);
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd LLL yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
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