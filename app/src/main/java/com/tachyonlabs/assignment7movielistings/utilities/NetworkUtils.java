package com.tachyonlabs.assignment7movielistings.utilities;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String API_KEY_PARAM = "api_key";
    final static String API_APPEND_TO_RESPONSE_PARAM = "append_to_response";
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String TMDB_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/now_playing";

    public static URL buildMoviesUrl(String tmdbApiKey) {
        Uri builtUri = Uri.parse(TMDB_MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, tmdbApiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(10000);
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
