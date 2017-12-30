package com.example.android.quakereport;

import android.os.AsyncTask;
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

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    /**
     * Sample JSON response for a USGS query
     */
    private static String SAMPLE_JSON_RESPONSE = "";




    public static List<EarthQuakes> fletchEarthquakes(String requesturl) {
        URL url = createurl(requesturl);
        String jsonResponse = null;
        try {
            jsonResponse = MakeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<EarthQuakes> earthQuakes = extractFretuesFromJson(jsonResponse);

        return earthQuakes;
    }

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */






    private static URL createurl(String Stringurl) {
        URL url = null;
        try {
            url = new URL(Stringurl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error in forming url " + e);
        }
        return url;
    }


    private static String MakeHttpRequest(URL url) throws IOException {
        if (url == null){
            return SAMPLE_JSON_RESPONSE;
        }
        HttpURLConnection urlConnection = null;
        InputStream input = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode()== 200) {
                input = urlConnection.getInputStream();
                SAMPLE_JSON_RESPONSE = readFromStream(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (input!=null)
            {
                input.close();
            }
        }
        return SAMPLE_JSON_RESPONSE;
    }



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

    private QueryUtils() {
    }

    /**
     * parsing a JSON response.
     */
    public static List<EarthQuakes> extractFretuesFromJson(String SAMPLE_JSON_RESPONSE) {

        if (TextUtils.isEmpty(SAMPLE_JSON_RESPONSE))
        {
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<EarthQuakes> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            JSONObject root = new JSONObject(SAMPLE_JSON_RESPONSE);
            Log.v("data", SAMPLE_JSON_RESPONSE);

            JSONArray Fetures = root.getJSONArray("features");

            for (int j = 0; j < Fetures.length(); j++) {
                JSONObject c = Fetures.getJSONObject(j);

                JSONObject properties = c.getJSONObject("properties");

                double mag = properties.getDouble("mag");
                long time = properties.getLong("time");
                String place = properties.getString("place");
                String url = properties.getString("url");
                earthquakes.add(new EarthQuakes(mag, place, time, url));
            }
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}