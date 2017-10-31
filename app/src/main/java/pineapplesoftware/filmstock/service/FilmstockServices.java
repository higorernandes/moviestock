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
    private static Call<JsonObject> mCall;
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
            if (mCall == null) {
                mCall = service.searchMovie(FilmstockApi.API_KEY, textToSearch, "full");
                mCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                        mCall = null;
                        if (response.isSuccess()) {
                            try {
                                // Getting the data from the Json response and transforming it into a movie list.
                                Gson gson = new Gson();
                                ArrayList<Movie> moviesList = new ArrayList<>();
                                JsonObject jsonObject = response.body().getAsJsonObject();

                                String json = jsonObject.getAsJsonObject().toString();
                                Movie movie = gson.fromJson(json, Movie.class);
                                if (Boolean.valueOf(movie.getResponse().toLowerCase())) {
                                    moviesList.add(movie);
                                }

                                listener.onResponseSuccess(moviesList);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            listener.onResponseError(context.getResources().getString(R.string.generic_server_error));
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        mCall = null;
                        listener.onResponseError(t.getMessage());
                    }
                });
            } else {
                mCall.cancel();
                mCall = null;
            }
        }
    }

    //endregion
}
