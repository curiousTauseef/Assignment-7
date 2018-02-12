package com.tachyonlabs.assignment7movielistings;

import com.tachyonlabs.assignment7movielistings.adapters.MovieAdapter;
import com.tachyonlabs.assignment7movielistings.databinding.ActivityMainBinding;
import com.tachyonlabs.assignment7movielistings.models.Movie;
import com.tachyonlabs.assignment7movielistings.utilities.NetworkUtils;
import com.tachyonlabs.assignment7movielistings.utilities.TmdbJsonUtils;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    ActivityMainBinding mBinding;
    RecyclerView mRecyclerView;
    MovieAdapter mMovieAdapter;
    Movie[] mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mRecyclerView = mBinding.rvMovieList;
        LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(mMovieAdapter);

        new LoadMoviesTask().execute();
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

    public class LoadMoviesTask extends AsyncTask<String, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            String tmdbApiKey = "put your API key here";
            URL moviesRequestUrl = NetworkUtils.buildMoviesUrl(tmdbApiKey);

            try {
                String jsonTmdbResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                Movie[] moviesFromJson = TmdbJsonUtils.getMoviesFromJson(MainActivity.this, jsonTmdbResponse);

                return moviesFromJson;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                mMovies = movies;
                mMovieAdapter.setMovieData(movies);
            } else {
            }
        }
    }
}
