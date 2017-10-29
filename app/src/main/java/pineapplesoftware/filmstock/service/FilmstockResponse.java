package pineapplesoftware.filmstock.service;

/**
 * Created by root on 2017-10-29.
 */

public interface FilmstockResponse<T>
{
    void onResponseSuccess(T response);
    void onResponseError(String message);
    void onResponseErrorNotFound();
}
