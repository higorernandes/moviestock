package pineapplesoftware.filmstock.model.domain;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public interface IFilmstockResponse<T>
{
    void onResponseSuccess(T response);
    void onResponseError(String message);
}
