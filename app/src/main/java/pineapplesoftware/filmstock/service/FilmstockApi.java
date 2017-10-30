package pineapplesoftware.filmstock.service;

import android.content.Context;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import pineapplesoftware.filmstock.model.domain.IFilmstockResponse;
import pineapplesoftware.filmstock.model.domain.MovieResponse;
import pineapplesoftware.filmstock.model.dto.Movie;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by root on 2017-10-29.
 */

public class FilmstockApi
{
    private static FilmstockApi.IFilmstockApi filmstockApi;
    private static String BASE_URL = "http://www.omdbapi.com";
    public static String API_KEY = "201fefa";

    public static Retrofit getClient(final Context context) {
        try {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static FilmstockApi.IFilmstockApi getApi(final Context context) {
        try {
            return getClient(context).create(FilmstockApi.IFilmstockApi.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //region Service calls interface

    public interface IFilmstockApi
    {
        @GET(".")
        Call<JsonObject> searchMovie(@Query("apikey") String apiKey,
                                     @Query("t") String text,
                                     @Query("plot") String plot);
    }

    //endregion
}
