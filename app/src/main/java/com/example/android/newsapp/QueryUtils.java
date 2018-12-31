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
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }


    // RAW TEST JSON DATA
    private static final String GUARDIAN_TEST_DATA = "{\"response\":{\"status\":\"ok\",\"userTier" +
            "\":\"developer\",\"total\":14473,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1," +
            "\"pages\":1448,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"politics/2018/dec/01" +
            "/may-v-corbyn-on-brexit-the-debate-over-the-debate\",\"type\":\"article\",\"sectionId\":" +
            "\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2018-12-01T07:00:15Z\"" +
            ",\"webTitle\":\"May v Corbyn on Brexit: the debate over the debate\",\"webUrl\":\"https:" +
            "//www.theguardian.com/politics/2018/dec/01/may-v-corbyn-on-brexit-the-debate-over-" +
            "the-debate\",\"apiUrl\":\"https://content.guardianapis.com/politics/2018/dec/01/may-v-" +
            "corbyn-on-brexit-the-debate-over-the-debate\",\"isHosted\":false,\"pillarId\":\"pillar/news" +
            "\",\"pillarName\":\"News\"},{\"id\":\"education/2018/nov/05/lets-have-perspective-in-tuition-" +
            "fees-debate\",\"type\":\"article\",\"sectionId\":\"education\",\"sectionName\":\"" +
            "Education\", \"webPublicationDate\":\"2018-11-05T18:20:40Z\",\"webTitle\":\"Let’s have perspective in tuition " +
            "fees debate | Letters\",\"webUrl\":\"https://www.theguardian.com/education/2018/nov/05/" +
            "lets-have-perspective-in-tuition-fees-debate\",\"apiUrl\":\"https://content." +
            "guardianapis.com/education/2018/nov/05/lets-have-perspective-in-tuition-fees-debate\",\"" +
            "isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"" +
            "politics/2018/dec/06/itv-scraps-plans-to-host-may-corbyn-brexit-debate\",\"type\"" +
            ":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"" +
            "webPublicationDate\":\"2018-12-06T19:55:30Z\",\"webTitle\":\"ITV scraps plans to " +
            "host May-Corbyn Brexit debate\",\"webUrl\":\"https://www.theguardian.com/politics/2018/dec/" +
            "06/itv-scraps-plans-to-host-may-corbyn-brexit-debate\",\"apiUrl\":\"https://content." +
            "guardianapis.com/politics/2018/dec/06/itv-scraps-plans-to-host-may-corbyn-brexit-debate" +
            "\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics/2018/" +
            "dec/04/bbc-cancels-plans-for-sunday-night-brexit-debate\",\"type\":\"article\",\"sectionId\":\"" +
            "politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2018-12-04T17:15:34Z\",\"webTitle" +
            "\":\"BBC cancels plans for Sunday night Brexit debate\",\"webUrl\":\"https://www.theguardian.com" +
            "/politics/2018/dec/04/bbc-cancels-plans-for-sunday-night-brexit-debate\",\"apiUrl\":\"https:" +
            "//content.guardianapis.com/politics/2018/dec/04/bbc-cancels-plans-for-sunday-night-brexit" +
            "-debate\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"" +
            "commentisfree/2018/dec/02/the-guardian-view-on-parliaments-brexit-debate-time-to-choose" +
            "\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate" +
            "\":\"2018-12-02T18:05:39Z\",\"webTitle\":\"The Guardian view on parliament’s Brexit debate: " +
            "time to choose | Editorial\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/" +
            "dec/02/the-guardian-view-on-parliaments-brexit-debate-time-to-choose\",\"apiUrl\":\"https:" +
            "//content.guardianapis.com/commentisfree/2018/dec/02/the-guardian-view-on-parliaments-" +
            "brexit-debate-time-to-choose\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName" +
            "\":\"Opinion\"},{\"id\":\"media/commentisfree/2018/dec/02/bbc-in-hot-water-over-debate-and-" +
            "an-unlikely-pastor\",\"type\":\"article\",\"sectionId\":\"media\",\"sectionName\":\"Media\",\"" +
            "webPublicationDate\":\"2018-12-02T14:00:36Z\",\"webTitle\":\"BBC in hot water over debate – " +
            "and an unlikely pastor | Jane Martinson\",\"webUrl\":\"https://www.theguardian." +
            "com/media/commentisfree/2018/dec/02/bbc-in-hot-water-over-debate-and-an-" +
            "unlikely-pastor\",\"apiUrl\":\"https://content.guardianapis.com/media/commentisfree" +
            "/2018/dec/02/bbc-in-hot-water-over-debate-and-an-unlikely-pastor\",\"isHosted\"" +
            ":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"politics" +
            "/2018/nov/16/female-peers-condemn-misogynistic-attitudes-in-lord-lester-debate" +
            "\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\"," +
            "\"webPublicationDate\":\"2018-11-16T18:50:43Z\",\"webTitle\":\"Female peers condemn " +
            "\'misogynistic\' attitudes in Lord Lester debate\",\"webUrl\":\"https://www.theguardian.com" +
            "/politics/2018/nov/16/female-peers-condemn-misogynistic-attitudes-in-lord-lester-debate" +
            "\",\"apiUrl\":\"https://content.guardianapis.com/politics/2018/nov/16/female-peers-condemn-" +
            "misogynistic-attitudes-in-lord-lester-debate\",\"isHosted\":false,\"pillarId\":\"" +
            "pillar/news\",\"pillarName\":\"News\"},{\"id\":\"commentisfree/2018/nov/14/" +
            "the-guardian-view-on-theresa-mays-deal-reset-the-debate\",\"type\":\"article\"," +
            "\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion" +
            "\",\"webPublicationDate\":\"2018-11-14T18:34:05Z\",\"webTitle\":\"The Guardian view on Theresa " +
            "May’s deal: reset the debate | Editorial\",\"webUrl\":\"https://www.theguardian.com/" +
            "commentisfree/2018/nov/14/the-guardian-view-on-theresa-mays-deal-reset-the-debate" +
            "\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/nov/14/the-guardian-" +
            "view-on-theresa-mays-deal-reset-the-debate\",\"isHosted\":false,\"pillarId\":\"pillar/" +
            "opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"politics/2018/oct/31/debate-final-brexit-" +
            "deal-could-use-rare-commons-procedure\",\"type\":\"article\",\"sectionId\":\"politics\",\"" +
            "sectionName\":\"Politics\",\"webPublicationDate\":\"2018-10-31T06:00:12Z\",\"webTitle\":\"Debate " +
            "on final Brexit deal could use rare Commons procedure\",\"webUrl\":\"https://www." +
            "theguardian.com/politics/2018/oct/31/debate-final-brexit-deal-could-use-rare-" +
            "commons-procedure\",\"apiUrl\":\"https://content.guardianapis.com/politics/2018/oct/31/" +
            "debate-final-brexit-deal-could-use-rare-commons-procedure\",\"isHosted\":false,\"pillarId" +
            "\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"news/2018/jul/18/debate-continues-over-" +
            "labours-code-on-antisemitism\",\"type\":\"article\",\"sectionId\":\"news\",\"sectionName" +
            "\":\"News\",\"webPublicationDate\":\"2018-07-18T17:10:48Z\",\"webTitle\":\"Debate continues over " +
            "Labour’s code on antisemitism | Letters\",\"webUrl\":\"https://www.theguardian.com/news" +
            "/2018/jul/18/debate-continues-over-labours-code-on-antisemitism\",\"apiUrl" +
            "\":\"https://content.guardianapis.com/news/2018/jul/18/debate-continues-over-labours-code-" +
            "on-antisemitism\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}}";





    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<News> extractFeatureFromJson(String earthquakeJSON) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<News> newsarticles = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Convert the JSON string to a JSON object
            //JSONObject root = new JSONObject(earthquakeJSON);
            JSONObject root = new JSONObject(GUARDIAN_TEST_DATA);

            // Get the response Object from the JSON object
            JSONObject responseObject = root.getJSONObject("response");

            // Get the results array from the response Object
            JSONArray resultsArray = responseObject.getJSONArray("results");

            // Iterate the results jsonArray to acquire individual object containing the news
            // sectionId.
            for(int i=0; i < resultsArray.length(); i++) {

                // Define string to hold required JSON strings
                String weburl = null;
                String webpublicationdate = null;
                String webtitle = null;
                String sectionname = null;

                // each loop step through each JSON object in the resultsArray
                JSONObject resultObject = resultsArray.getJSONObject(i);

                // extract the sectionId string so it can be checked
                String sectionid = resultObject.optString("sectionId");

                // Check sectionId for string = news
                // If it is the news sectionId acquire the required details
                if (sectionid == "news") {
                    // extract the earthquate url
                    weburl = responseObject.optString("webUrl");
                    webpublicationdate = responseObject.optString("webPublicationDate");
                    webtitle = responseObject.optString("webTitle");
                    sectionname = responseObject.optString("sectionName");
                }

                // create a news object from the acquired data and add it to the
                // news ArrayList
                newsarticles.add(new News(weburl, webpublicationdate, webtitle, sectionname));
                Log.i("QueryUtils", "Finished parsing JSON Object.");
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
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
     * Query the USGS dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchEarthquakeData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.i(LOG_TAG, "In fetchEarthquakeData()");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return news;
    }
}