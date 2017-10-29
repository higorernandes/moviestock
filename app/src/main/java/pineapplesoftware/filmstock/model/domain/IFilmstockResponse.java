package pineapplesoftware.filmstock.model.domain;

/**
 * Created by root on 2017-10-29.
 */

public interface IFilmstockResponse<T>
{
    void onResponseSuccess(T response);
    void onResponseError(String message);
}
