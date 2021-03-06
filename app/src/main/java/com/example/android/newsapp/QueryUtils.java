package com.example.android.newsapp;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving News data from Guardian.
 */
public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<News> extractFeatureFromJson(String newsJSON) {

        // Create an empty ArrayList that we can start adding new to
        List<News> newsarticles = new ArrayList<>();

        // Try to parse the JSON. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Convert the JSON string to a JSON object
            JSONObject root = new JSONObject(newsJSON);

            // Get the response Object from the JSON object
            JSONObject responseObject = root.getJSONObject("response");

            // Get the results array from the response Object
            JSONArray resultsArray = responseObject.getJSONArray("results");

            // Iterate the results jsonArray to acquire individual object containing the news
            // sectionId.
            for (int i = 0; i < resultsArray.length(); i++) {

                // each loop step through each JSON object in the resultsArray
                JSONObject resultObject = resultsArray.getJSONObject(i);

                // Define string to hold required JSON strings
                String weburl = null;
                String webpublicationdate = null;
                String webtitle = null;
                String sectionname = null;
                ArrayList<String> authors = new ArrayList<>();

                // Check sectionId for string = news
                // If it is the news sectionId acquire the required details
                weburl = resultObject.optString("webUrl");
                webpublicationdate = resultObject.optString("webPublicationDate");
                webtitle = resultObject.optString("webTitle");
                sectionname = resultObject.optString("sectionName");

                if (resultObject.getJSONArray("tags") != null) {

                    JSONArray tagsArray = resultObject.getJSONArray("tags");

                    // Iterate the tags jsonArray to acquire the author/s of the news article
                    for (int j = 0; j < tagsArray.length(); j++) {
                        // Extract the tags object
                        JSONObject tagsObject = tagsArray.getJSONObject(j);
                        // check if the author name exists (stored in webTitle of tags object)
                        if (tagsObject.getString("webTitle") != null) {
                            authors.add(tagsObject.getString("webTitle"));
                        }
                    }
                }

                // create a news object from the acquired data and add it to the news ArrayList
                newsarticles.add(new News(weburl, webpublicationdate, webtitle, sectionname, authors));
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        // Return the list of news'
        return newsarticles;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the Guardian data set and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link News}s
        // Return the list of {@link News}s
        return extractFeatureFromJson(jsonResponse);
    }
}