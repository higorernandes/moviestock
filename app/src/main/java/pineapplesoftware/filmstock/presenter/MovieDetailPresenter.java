package pineapplesoftware.filmstock.presenter;

import pineapplesoftware.filmstock.generic.IPresenter;
import pineapplesoftware.filmstock.generic.IView;
import pineapplesoftware.filmstock.helper.NetworkHelper;
import pineapplesoftware.filmstock.model.domain.IFilmstockResponse;
import pineapplesoftware.filmstock.model.dto.Movie;
import pineapplesoftware.filmstock.service.FilmstockServices;
import pineapplesoftware.filmstock.view.IMovieDetailView;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public class MovieDetailPresenter implements IPresenter {

    private IMovieDetailView mView;

    @Override
    public void setView(IView view) {
        this.mView = (IMovieDetailView) view;
    }

    @Override
    public void clearView() {
        this.mView = new IMovieDetailView.MovieDetailEmptyView();
    }

    /**
     * Searches for a movie given a typed text and retrieves a list of movies.
     * @param imdbId The text to be searched.
     */
    public void loadMovieInformation(String imdbId) {
        if(NetworkHelper.isConnected()) {
            mView.showLoading();
            FilmstockServices.loadMovieInformation(mView.getContext(), imdbId, new IFilmstockResponse<Movie>() {
                @Override
                public void onResponseSuccess(Movie response) {
                    mView.callbackSuccessLoadMovie(response);
                    mView.hideLoading();
                }

                @Override
                public void onResponseError(String message) {
                    mView.callbackErrorLoadMovie();
                    mView.hideLoading();
                }
            });
        } else {
            mView.requestCallbackError();
        }
    }
}
