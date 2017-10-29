package pineapplesoftware.filmstock.model.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import pineapplesoftware.filmstock.model.dto.Movie;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public class MovieResponse
{
    @SerializedName("items")
    @Expose
    private ArrayList<Movie> mMovies;

    public ArrayList<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.mMovies = movies;
    }
}
