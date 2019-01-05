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

    private ArrayList<String> authorsArray;

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

        // Get the {@link News} object located at this position in the list
        News currentNews = getItem(position);

        // get title text from currentNews and slip it at the desired point
        String titleString = currentNews.getNewsTitle();

        // get title text from currentNews and slip it at the desired point
        String sectionString = currentNews.getSectionName();

        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView newstitleTextView = listItemView.findViewById(R.id.newstitle);

        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView sectionTextView = listItemView.findViewById(R.id.articletype);

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

        // add the news title text to the text view
        newstitleTextView.setText(titleString);

        // add the section news type to the text view
        sectionTextView.setText(sectionString);

        // add the authors to the text view
        // get title text from currentNews and slip it at the desired point
        authorsArray = currentNews.getAuthors();
        // Turn array of authors names into a string
        String authorsString = authorArrayToString(authorsArray);
        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView authorsTextView = listItemView.findViewById(R.id.authors);
        // add the authors to the text view
        authorsTextView.setText(authorsString);

        // Return the whole list item layout
        // so that it can be shown in the ListView
        return listItemView;
    }

    // Convert array of authors to a string
    private String authorArrayToString(ArrayList<String> authors) {
        StringBuilder authorsString = new StringBuilder();
        if (authors.size() > 0) {
            for (int i = 0; i < authors.size(); i++) {
                authorsString.append(authors.get(i));
                if (i < (authors.size() - 1)) {
                    authorsString.append(", ");
                }
            }
        } else {
            authorsString.append(getContext().getResources().getString(R.string.no_author));
        }
        return authorsString.toString();
    }

    /**
     * Convert date string from JSON into simpledateformat
     */
    private Date dateCreator(String createDate) throws ParseException {
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