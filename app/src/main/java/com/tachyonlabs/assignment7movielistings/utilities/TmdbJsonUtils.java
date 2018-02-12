package com.tachyonlabs.assignment7movielistings.utilities;

import com.tachyonlabs.assignment7movielistings.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class TmdbJsonUtils {
    private static final String TAG = TmdbJsonUtils.class.getSimpleName();

    public static Movie[] getMoviesFromJson(Context context, String moviesJsonStr) throws JSONException {
        JSONObject movieDataJson = new JSONObject(moviesJsonStr);

        JSONArray resultsArray = movieDataJson.getJSONArray("results");

        Movie[] movies = new Movie[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {
            Movie movie = new Movie();
            JSONObject result = resultsArray.getJSONObject(i);
            movie.setTitle(result.getString("title"));
            movie.setPosterUrl(result.getString("poster_path"));
            movie.setOverview(result.getString("overview"));
            movies[i] = movie;
        }
        return movies;
    }
}