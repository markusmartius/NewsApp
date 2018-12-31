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

    private String offsetText;
    private String primaryText;
    private String locationString;

    // New constructor
    public NewsAdapter(Activity context, ArrayList<News> nameForArrayOfEarthquakeObjects) {
        // the zero means we're initially not passing a view as it will be passed later???
        super(context, 0, nameForArrayOfEarthquakeObjects);
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
        News currentEarthquake = getItem(position);

        // Find the TextView in the list_item.settings_main layout with the ID version_name
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // get the magnitude string for this earthquake in the convert method
        // change magnitude to be a one decimal place value
        // converts Double to String as well
        String newMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        // Get the earthquake magnitude from the current Earthquake object and
        // set this text on the name TextView
        magnitudeTextView.setText(newMagnitude);

        // get location text from currentEarthquake and slip it at the desired point
        locationString = currentEarthquake.getLocation();

        if (locationString.contains("of")) {
            String[] parts = locationString.split("(?<=of)");
            offsetText = parts[0].trim();
            primaryText = parts[1].trim();
        } else {
            offsetText = "Near the";
        }
        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView offsetTextView = (TextView) listItemView.findViewById(R.id.location_offset);
        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView primaryTextView = (TextView) listItemView.findViewById(R.id.primary_location);
        // add the offset location text to the text view
        offsetTextView.setText(offsetText);
        // set the primary location text to the text view
        primaryTextView.setText(primaryText);

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Get the earthquake date from the current Earthquake object and
        // set this text on the name TextView
        dateTextView.setText(formattedDate);

        // Find the TextView in the list_item.settings_main layout with the ID version_number
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Get the earthquake date from the current Earthquake object and
        // set this text on the name TextView
        timeTextView.setText(formattedTime);

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    // Method to define the colour to use with the magnitude
    private int getMagnitudeColor(double magnitude) {

        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);

        switch (magnitudeFloor) {
            case 0:
            case 1:  magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:  magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:  magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:  magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:  magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:  magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:  magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:  magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:  magnitudeColorResourceId = R.color.magnitude9;
                break;
            default: magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
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