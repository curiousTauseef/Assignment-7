package com.tachyonlabs.assignment7movielistings.adapters;

import com.squareup.picasso.Picasso;
import com.tachyonlabs.assignment7movielistings.R;
import com.tachyonlabs.assignment7movielistings.models.Movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String POSTERS_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String POSTER_WIDTH = "w185/";
    private final static String TAG = MovieAdapter.class.getSimpleName();
    private final MovieAdapterOnClickHandler mClickHandler;
    private Movie[] mMovies;

    public MovieAdapter(MovieAdapterOnClickHandler movieAdapterOnClickHandler) {
        mClickHandler = movieAdapterOnClickHandler;
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_movie;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MovieAdapterViewHolder viewHolder = new MovieAdapterViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String posterUrl = POSTERS_BASE_URL + POSTER_WIDTH + mMovies[position].getPosterUrl();
        Log.d(TAG, posterUrl);
        Picasso.with(holder.ivPoster.getContext())
                .load(posterUrl)
                .into(holder.ivPoster);

        String movieTitle = mMovies[position].getTitle();
        holder.tvTitle.setText(movieTitle);

        String movieOverview = mMovies[position].getOverview();
        holder.tvOverview.setText(movieOverview);
    }

    public void setMovieData(Movie[] movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        } else {
            return mMovies.length;
        }
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie clickedItem);
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView ivPoster;
        public final TextView tvTitle;
        public final TextView tvOverview;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Movie movie = mMovies[getAdapterPosition()];
            mClickHandler.onClick(movie);
        }
    }
}
