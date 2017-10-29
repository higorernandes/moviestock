package pineapplesoftware.filmstock.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by root on 2017-10-29.
 */

public class FilmstockApi
{
    private static FilmstockApi.IFilmstockApi guideApi;
    private static String BASE_URL = "http://www.omdbapi.com/?apikey=[getsomeapikey]&";

    public static Retrofit getClient(final Context context) {
        try {
            Gson gson = new GsonBuilder().create();
            return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static FilmstockApi.IFilmstockApi getApi(final Context context) {
        try {
            guideApi = getClient(context).create(FilmstockApi.IFilmstockApi.class);
            return guideApi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //region Service calls interface

    public interface IFilmstockApi
    {
        @GET("t={search}")
        Call<JsonObject> searchMovie(@Query("search") String text);
    }

    //endregion
}
