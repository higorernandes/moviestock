package pineapplesoftware.moviestock.service;

import android.content.Context;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */
class MoviestockApi
{
    private static final String BASE_URL = "http://www.omdbapi.com";
    static final String API_KEY = "201fefa";
    static final String PLOT_FULL = "full";

    private static Retrofit getClient(final Context context) {
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

    static MoviestockApi.IMoviestockApi getApi(final Context context) {
        try {
            return getClient(context).create(MoviestockApi.IMoviestockApi.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //region Service calls interface

    public interface IMoviestockApi
    {
        @GET(".")
        Call<JsonObject> searchMovie(@Query("apikey") String apiKey,
                                     @Query("t") String text,
                                     @Query("plot") String plot);

        @GET(".")
        Call<JsonObject> searchMoviePaginated(@Query("apikey") String apiKey,
                                              @Query("s") String text,
                                              @Query("page") int page,
                                              @Query("plot") String plot);

        @GET(".")
        Call<JsonObject> loadMovieInformation(@Query("apikey") String apiKey,
                                              @Query("i") String imdbId,
                                              @Query("plot") String plot);
    }

    //endregion
}
