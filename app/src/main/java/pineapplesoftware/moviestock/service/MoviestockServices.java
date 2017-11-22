package pineapplesoftware.moviestock.service;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import pineapplesoftware.moviestock.R;
import pineapplesoftware.moviestock.model.domain.IMoviestockResponse;
import pineapplesoftware.moviestock.model.domain.MovieSearchResult;
import pineapplesoftware.moviestock.model.domain.Search;
import pineapplesoftware.moviestock.model.dto.Movie;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public class MoviestockServices
{
    private static Call<JsonObject> mCall;
    private static MoviestockApi.IMoviestockApi getMoviestockApi(Context context) {
        return MoviestockApi.getApi(context);
    }

    //region Service Calls

    /**
     * Makes a request to the OMDB Api to get a movie given a text to be searched.
     * @param context The calling class.
     * @param textToSearch The text to be searched.
     * @param listener The class which will be listening to the response.
     */
    public static void searchMovie(final Context context, String textToSearch, final IMoviestockResponse listener) {
        MoviestockApi.IMoviestockApi service = getMoviestockApi(context);
        if (service != null) {
            if (mCall != null) {
                mCall.cancel();
            }

            mCall = service.searchMovie(MoviestockApi.API_KEY, textToSearch, MoviestockApi.PLOT_FULL);
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
        }
    }

    /**
     * Makes a request to the OMDB Api to get a movie given a text to be searched.
     * @param context The calling class.
     * @param textToSearch The text to be searched.
     * @param listener The class which will be listening to the response.
     */
    public static void searchMoviePaginated(final Context context, String textToSearch, int page, final IMoviestockResponse listener) {
        MoviestockApi.IMoviestockApi service = getMoviestockApi(context);
        if (service != null) {
            if (mCall != null) {
                mCall.cancel();
            }

            mCall = service.searchMoviePaginated(MoviestockApi.API_KEY, textToSearch, page,MoviestockApi.PLOT_FULL);
            mCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                    mCall = null;
                    if (response.isSuccess()) {
                        try {
                            // Getting the data from the Json response and transforming it into a movie list.
                            Gson gson = new Gson();
                            JsonObject jsonObject = response.body().getAsJsonObject();

                            String json = jsonObject.getAsJsonObject().toString();
                            MovieSearchResult movieSearchResult = gson.fromJson(json, MovieSearchResult.class);

                            if (Boolean.valueOf(movieSearchResult.getResponse().toLowerCase())) {
                                ArrayList<Search> searchResultsList = new ArrayList<>();
                                if (movieSearchResult.getSearch() != null) {
                                    searchResultsList.addAll(movieSearchResult.getSearch());
                                }

                                listener.onResponseSuccess(searchResultsList);
                            } else {
                                listener.onResponseError(movieSearchResult.getError());
                            }
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
        }
    }

    /**
     * Loads the information for a single movie.
     * @param context The calling class.
     * @param imdbId The movie's IMDB ID.
     * @param listener The class which will be listening to the response.
     */
    public static void loadMovieInformation(final Context context, String imdbId, final IMoviestockResponse listener) {
        MoviestockApi.IMoviestockApi service = getMoviestockApi(context);
        if (service != null) {
            if (mCall != null) {
                mCall.cancel();
            }

            mCall = service.loadMovieInformation(MoviestockApi.API_KEY, imdbId, MoviestockApi.PLOT_FULL);
            mCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                    mCall = null;
                    if (response.isSuccess()) {
                        try {
                            // Getting the data from the Json response and transforming it into a movie list.
                            Gson gson = new Gson();
                            JsonObject jsonObject = response.body().getAsJsonObject();

                            String json = jsonObject.getAsJsonObject().toString();
                            Movie movie = gson.fromJson(json, Movie.class);

                            listener.onResponseSuccess(movie);
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
        }
    }

    //endregion
}
