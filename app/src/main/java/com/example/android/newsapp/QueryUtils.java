package com.example.android.newsapp;

import android.text.TextUtils;
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


public final class QueryUtils {

    private static final String LOG_TAG = ArticleActivity.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Article} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Article> extractFeatureFromJson(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding articles to
        List<Article> articles = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            // Extract the JSONObject associated with the key called "response",
            // which represents a list of items (or articles).

            JSONObject articleObject = baseJsonResponse.getJSONObject("response");
            JSONArray articleResultsArray = articleObject.getJSONArray("results");

            for (int i = 0; i < articleResultsArray.length(); i++) {

                // Get a single article at position i within the list of articles
                JSONObject currentArticle = articleResultsArray.getJSONObject(i);

                // Extract the value for the key called "webTitle"
                String webTitle = currentArticle.getString("webTitle");

                // Extract the value for the key called "sectionName"
                String sectionName = currentArticle.getString("sectionName");

                // Extract the value for the key called "webUrl"
                String webUrl = currentArticle.getString("webUrl");

                String webPublicationDate;

                String webPublicationDateAndTime;

                JSONArray tags = currentArticle.getJSONArray("tags");

                String webAuthor = "";

                //Check to see if there is a publication date. if so, extract it
                //separate out just the date portion

                if (currentArticle.has("webPublicationDate")) {

                    webPublicationDateAndTime = currentArticle.getString("webPublicationDate");
                    webPublicationDate = webPublicationDateAndTime.substring(0, 10);


                } else {

                    webPublicationDate = " Unknown";
                }

                // check to see if tags key is empty
                if (tags.length() != 0) {

                    for (int j = 0; j < tags.length(); j++) {

                        JSONObject tagsObject = tags.getJSONObject(j);

                        // if not last author, then add word "and" between authors
                        if (j != tags.length() - 1) {

                            webAuthor = webAuthor + tagsObject.getString("webTitle") + " and ";

                        } else {
                            webAuthor = webAuthor + tagsObject.getString("webTitle");

                        }

                    } //End tags.length iteration

                } else {

                    webAuthor = " Unknown author";
                }


                // Create a new {@link Article} object with the webtitle,sectionName,webPublicationDate
                //and webAuthor from the JSON response.
                Article article = new Article(webTitle, sectionName, webUrl, webPublicationDate, webAuthor);

                // Add the new {@link Article} to the list of articles.
                articles.add(article);

            } //End of articleResultsArray iteration

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }

        // Return the list of articles
        return articles;

    }// End of extractFeatureFromJson class

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
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
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
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
     * Query the GUARDIAN dataset and return a list of {@link Article} objects.
     */
    public static List<Article> fetchArticleData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Article}s
        List<Article> articles = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Article}s
        return articles;
    }

}//End of QueryUtils class
