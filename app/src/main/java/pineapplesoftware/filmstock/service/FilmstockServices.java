package pineapplesoftware.filmstock.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import pineapplesoftware.filmstock.R;
import pineapplesoftware.filmstock.model.domain.IFilmstockResponse;
import pineapplesoftware.filmstock.model.domain.MovieResponse;
import pineapplesoftware.filmstock.model.dto.Movie;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public class FilmstockServices
{
    private static FilmstockApi.IFilmstockApi getFilmstockApi(Context context) {
        return FilmstockApi.getApi(context);
    }

    //region Service Calls

    /**
     * Makes a request to the OMDB Api to get a movie given a text to be searched.
     * @param context The calling class.
     * @param textToSearch The text to be searched.
     * @param listener The class which will be listening to the response.
     */
    public static void searchMovie(final Context context, String textToSearch, final IFilmstockResponse listener) {
        FilmstockApi.IFilmstockApi service = getFilmstockApi(context);
        if (service != null) {
            service.searchMovie(FilmstockApi.API_KEY, textToSearch).enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Response<MovieResponse> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        try {
                            // Getting the data from the Json response and transforming it into a movie list.
                            listener.onResponseSuccess(response.body().getMovies());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        listener.onResponseError(context.getResources().getString(R.string.generic_server_error));
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    listener.onResponseError(context.getResources().getString(R.string.generic_server_error));
                }
            });
        }
    }

    //endregion
}
