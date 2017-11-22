package pineapplesoftware.moviestock.model.domain;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public interface IMoviestockResponse<T>
{
    void onResponseSuccess(T response);
    void onResponseError(String message);
}
