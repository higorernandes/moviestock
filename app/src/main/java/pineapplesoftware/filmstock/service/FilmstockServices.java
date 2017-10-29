package pineapplesoftware.filmstock.service;

import android.content.Context;

import com.google.gson.JsonObject;

import pineapplesoftware.filmstock.model.domain.IFilmstockResponse;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by root on 2017-10-29.
 */

public class FilmstockServices
{
    private static FilmstockApi.IFilmstockApi getFilmstockApi(Context context) {
        return FilmstockApi.getApi(context);
    }

    //region Service Calls

    public static void searchMovie(final Context context, String textToSearch, final IFilmstockResponse listener) {
        FilmstockApi.IFilmstockApi service = getFilmstockApi(context);
        if (service != null) {
            service.searchMovie(textToSearch).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                    listener.onResponseSuccess(response);
                }

                @Override
                public void onFailure(Throwable t) {
                    listener.onResponseError(t.getMessage());
                }
            });
        }
    }

    //endregion
}
