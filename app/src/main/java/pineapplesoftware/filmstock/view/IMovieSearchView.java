package pineapplesoftware.filmstock.view;

import android.content.Context;

import java.util.List;

import pineapplesoftware.filmstock.generic.IView;
import pineapplesoftware.filmstock.model.dto.Movie;

/**
 * Created by root on 2017-10-29.
 */

public interface IMovieSearchView extends IView
{

    void callbackSuccessSearchMovie(List<Movie> movieList);
    void callbackErrorSearchMovie();

    class HomebrokerEmptyView implements IMovieSearchView {

        @Override
        public Context getContext() {
            return null;
        }

        @Override
        public void requestCallbackSuccess() {

        }

        @Override
        public void requestCallbackError() {

        }

        @Override
        public void showLoading() {

        }

        @Override
        public void hideLoading() {

        }

        @Override
        public void callbackSuccessSearchMovie(List<Movie> movieList) {

        }

        @Override
        public void callbackErrorSearchMovie() {

        }
    }
}
