package pineapplesoftware.moviestock.view;

import android.content.Context;

import java.util.ArrayList;

import pineapplesoftware.moviestock.generic.IView;
import pineapplesoftware.moviestock.model.domain.Search;
import pineapplesoftware.moviestock.model.dto.Movie;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public interface IMovieSearchView extends IView
{
    void callbackSuccessSearchMovie(ArrayList<Movie> movieList);
    void callbackErrorSearchMovie();
    void callbackSuccessSearchMoviePaginated(ArrayList<Search> searchResults);
    void callbackErrorSearchMoviePaginated(String message);

    class MovieSearchEmptyView implements IMovieSearchView {

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
        public void callbackSuccessSearchMovie(ArrayList<Movie> movieList) { }

        @Override
        public void callbackErrorSearchMovie() { }

        @Override
        public void callbackSuccessSearchMoviePaginated(ArrayList<Search> movieList) { }

        @Override
        public void callbackErrorSearchMoviePaginated(String message) { }
    }
}
