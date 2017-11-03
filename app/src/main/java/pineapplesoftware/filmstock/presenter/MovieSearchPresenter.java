package pineapplesoftware.filmstock.presenter;

import java.util.ArrayList;

import pineapplesoftware.filmstock.generic.IPresenter;
import pineapplesoftware.filmstock.generic.IView;
import pineapplesoftware.filmstock.helper.NetworkHelper;
import pineapplesoftware.filmstock.model.domain.IFilmstockResponse;
import pineapplesoftware.filmstock.model.domain.Search;
import pineapplesoftware.filmstock.model.dto.Movie;
import pineapplesoftware.filmstock.service.FilmstockApi;
import pineapplesoftware.filmstock.service.FilmstockServices;
import pineapplesoftware.filmstock.view.IMovieSearchView;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public class MovieSearchPresenter implements IPresenter
{
    private IMovieSearchView mView;

    @Override
    public void setView(IView view) {
        this.mView = (IMovieSearchView) view;
    }

    @Override
    public void clearView() {
        this.mView = new IMovieSearchView.MovieSearchEmptyView();
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

    /**
     * Searches for a movie given a typed text and retrieves a list of movies, paginated.
     * @param textToSearch The text to be searched.
     * @param page The page to retrieve.
     */
    public void searchMoviePaginated(String textToSearch, int page) {
        if(NetworkHelper.isConnected()) {
            mView.showLoading();
            FilmstockServices.searchMoviePaginated(mView.getContext(), textToSearch, page, new IFilmstockResponse<ArrayList<Search>>() {
                @Override
                public void onResponseSuccess(ArrayList<Search> response) {
                    mView.callbackSuccessSearchMoviePaginated(response);
                    mView.hideLoading();
                }

                @Override
                public void onResponseError(String message) {
                    mView.callbackErrorSearchMoviePaginated(message);
                    mView.hideLoading();
                }
            });
        } else {
            mView.requestCallbackError();
        }
    }
}
