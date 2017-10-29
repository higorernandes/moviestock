package pineapplesoftware.filmstock.presenter;

import java.util.ArrayList;
import java.util.List;

import pineapplesoftware.filmstock.R;
import pineapplesoftware.filmstock.generic.IPresenter;
import pineapplesoftware.filmstock.generic.IView;
import pineapplesoftware.filmstock.helper.NetworkHelper;
import pineapplesoftware.filmstock.model.domain.IFilmstockResponse;
import pineapplesoftware.filmstock.model.dto.Movie;
import pineapplesoftware.filmstock.service.FilmstockServices;
import pineapplesoftware.filmstock.view.IMovieSearchView;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public class MovieSearchPresenter implements IPresenter {

    private IMovieSearchView mView;

    @Override
    public void setView(IView view) {
        this.mView = (IMovieSearchView) view;
    }

    @Override
    public void clearView() {
        this.mView = new IMovieSearchView.HomebrokerEmptyView();
    }

    /**
     * Searches for a movie given a typed text and retrieves a list of movies.
     * @param textToSearch The text to be searched.
     */
    public void searchMovie(String textToSearch) {
        if(NetworkHelper.isConnected()) {
            mView.showLoading();
            FilmstockServices.searchMovie(mView.getContext(), textToSearch, new IFilmstockResponse<ArrayList<Movie>>() {
                @Override
                public void onResponseSuccess(ArrayList<Movie> response) {
                    mView.callbackSuccessSearchMovie(response);
                    mView.hideLoading();
                }

                @Override
                public void onResponseError(String message) {
                    mView.callbackErrorSearchMovie();
                    mView.hideLoading();
                }
            });
        } else {
            mView.requestCallbackError();
        }
    }
}
