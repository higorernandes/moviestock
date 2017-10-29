package pineapplesoftware.filmstock.presenter;

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
 * Created by root on 2017-10-29.
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

    public void searchMovie(String textToSearch) {
        if(NetworkHelper.isConnected()) {
            mView.showLoading();
            FilmstockServices.searchMovie(mView.getContext(), textToSearch, new IFilmstockResponse<List<Movie>>() {
                @Override
                public void onResponseSuccess(List<Movie> response) {
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
