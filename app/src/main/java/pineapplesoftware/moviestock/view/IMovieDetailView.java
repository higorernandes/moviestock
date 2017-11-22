package pineapplesoftware.moviestock.view;

import android.content.Context;

import pineapplesoftware.moviestock.generic.IView;
import pineapplesoftware.moviestock.model.dto.Movie;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public interface IMovieDetailView extends IView
{

    void callbackSuccessLoadMovie(Movie movie);
    void callbackErrorLoadMovie();

    class MovieDetailEmptyView implements IMovieDetailView {

        @Override
        public Context getContext() {
            return null;
        }

        @Override
        public void requestCallbackSuccess() { }

        @Override
        public void requestCallbackError() { }

        @Override
        public void showLoading() { }

        @Override
        public void hideLoading() { }

        @Override
        public void callbackSuccessLoadMovie(Movie movie) { }

        @Override
        public void callbackErrorLoadMovie() { }
    }
}
