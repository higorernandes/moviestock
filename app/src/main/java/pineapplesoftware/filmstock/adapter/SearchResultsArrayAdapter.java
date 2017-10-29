package pineapplesoftware.filmstock.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import pineapplesoftware.filmstock.R;
import pineapplesoftware.filmstock.model.dto.Movie;

/**
 * Created by root on 2017-10-28.
 */

public class SearchResultsArrayAdapter extends RecyclerView.Adapter<SearchResultsArrayAdapter.MoviesHolder>
{
    //region Attributes

    private Context mContext;
    private ArrayList<Movie> mObjects;
    private IMovieSelectionListener mListener;

    //endregion

    //region Interface

    public interface IMovieSelectionListener {
        void onMovieItemSelected(int position);
    }

    //endregion

    //region Constructor

    public SearchResultsArrayAdapter(Context context, ArrayList<Movie> searchResults) {
        mContext = context;
        mObjects = searchResults;
    }

    //endregion

    //region Overridden Methods

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new MoviesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {
        Movie movie = mObjects.get(position);

        // Setting the movie poster.
        Glide.with(mContext).load(movie.getPosterUrl()).into(holder.getMoviePosterImageView());

        // Setting the movie title.
        holder.getMovieNameTextView().setText(movie.getTitle());

        //Setting the movie genre.
        holder.getMovieGenreTextView().setText(movie.getGenre());
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    //endregion

    //region Public Methods

    public void setListener(IMovieSelectionListener listener) {
        try {
            mListener = listener;
        } catch (ClassCastException e) {
            throw new ClassCastException(listener.toString() + "must implement IMovieSelectionListener.");
        }
    }

    //endregion

    //region Inner Classes

    class MoviesHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private CardView mRootView;
        private ImageView mMoviePosterImageView;
        private TextView mMovieNameTextView;
        private TextView mMovieGenreTextView;

        MoviesHolder(View itemView) {
            super(itemView);

            mRootView = itemView.findViewById(R.id.search_item_cardview);
            mMoviePosterImageView = itemView.findViewById(R.id.search_item_movie_poster);
            mMovieNameTextView = itemView.findViewById(R.id.search_item_movie_title);
            mMovieGenreTextView = itemView.findViewById(R.id.search_item_movie_genre);

            mRootView.setOnClickListener(this);
        }

        public ImageView getMoviePosterImageView() {
            return mMoviePosterImageView;
        }

        public void setMoviePosterImageView(ImageView moviePosterImageView) {
            this.mMoviePosterImageView = moviePosterImageView;
        }

        public TextView getMovieNameTextView() {
            return mMovieNameTextView;
        }

        public void setMovieNameTextView(TextView movieNameTextView) {
            this.mMovieNameTextView = movieNameTextView;
        }

        public TextView getMovieGenreTextView() {
            return mMovieGenreTextView;
        }

        public void setMovieGenreTextView(TextView movieGenreTextView) {
            this.mMovieGenreTextView = movieGenreTextView;
        }

        @Override
        public void onClick(View view) {
            mListener.onMovieItemSelected(getAdapterPosition());
        }
    }

    //endregion
}
