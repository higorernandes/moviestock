package pineapplesoftware.moviestock.presenter;

import pineapplesoftware.moviestock.generic.IPresenter;
import pineapplesoftware.moviestock.generic.IView;
import pineapplesoftware.moviestock.helper.NetworkHelper;
import pineapplesoftware.moviestock.model.domain.IMoviestockResponse;
import pineapplesoftware.moviestock.model.dto.Movie;
import pineapplesoftware.moviestock.service.MoviestockServices;
import pineapplesoftware.moviestock.view.IMovieDetailView;

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
            MoviestockServices.loadMovieInformation(mView.getContext(), imdbId, new IMoviestockResponse<Movie>() {
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
