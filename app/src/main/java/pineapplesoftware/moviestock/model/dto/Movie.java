package pineapplesoftware.moviestock.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Higor Ernandes on 2017-10-29.
 */

public class Movie
{
    private long mId;
    private Date mDateSaved;

    @SerializedName("Title")
    @Expose
    private String mTitle;

    @SerializedName("Year")
    @Expose
    private String mYear;

    @SerializedName("Rated")
    @Expose
    private String mRated;

    @SerializedName("Released")
    @Expose
    private String mReleased;

    @SerializedName("Runtime")
    @Expose
    private String mRuntime;

    @SerializedName("Genre")
    @Expose
    private String mGenre;

    @SerializedName("Director")
    @Expose
    private String mDirector;

    @SerializedName("Writer")
    @Expose
    private String mWriter;

    @SerializedName("Actors")
    @Expose
    private String mActors;

    @SerializedName("Plot")
    @Expose
    private String mPlot;

    @SerializedName("Language")
    @Expose
    private String mLanguage;

    @SerializedName("Country")
    @Expose
    private String mCountry;

    @SerializedName("Awards")
    @Expose
    private String mAwards;

    @SerializedName("Poster")
    @Expose
    private String mPosterUrl;

    @SerializedName("Metascore")
    @Expose
    private String mMetascore;

    @SerializedName("imdbRating")
    @Expose
    private String mImdbRating;

    @SerializedName("imdbVotes")
    @Expose
    private String mImdbVotes;

    @SerializedName("imdbID")
    @Expose
    private String mImdbId;

    @SerializedName("Type")
    @Expose
    private String mType;

    @SerializedName("DVD")
    @Expose
    private String mDvd;

    @SerializedName("BoxOffice")
    @Expose
    private String mBoxOffice;

    @SerializedName("Production")
    @Expose
    private String mProduction;

    @SerializedName("Website")
    @Expose
    private String mWebsite;

    @SerializedName("Response")
    @Expose
    private String mResponse;

    public Movie() {}

    public Movie(long id, String title, String year, String rated, String released, String runtime,
                 String genre, String director, String writer, String actors, String plot,
                 String language, String country, String awards, String posterUrl, String metascore,
                 String imdbRating, String imdbVotes, String imdbId, String type, String dvd,
                 String boxOffice, String production, String website, String response) {
        this.mId = id;
        this.mTitle = title;
        this.mYear = year;
        this.mRated = rated;
        this.mReleased = released;
        this.mRuntime = runtime;
        this.mGenre = genre;
        this.mDirector = director;
        this.mWriter = writer;
        this.mActors = actors;
        this.mPlot = plot;
        this.mLanguage = language;
        this.mCountry = country;
        this.mAwards = awards;
        this.mPosterUrl = posterUrl;
        this.mMetascore = metascore;
        this.mImdbRating = imdbRating;
        this.mImdbVotes = imdbVotes;
        this.mImdbId = imdbId;
        this.mType = type;
        this.mDvd = dvd;
        this.mBoxOffice = boxOffice;
        this.mProduction = production;
        this.mWebsite = website;
        this.mResponse = response;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public Date getDateSaved() {
        return mDateSaved;
    }

    public void setDateSaved(Date dateSaved) {
        this.mDateSaved = dateSaved;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        this.mYear = year;
    }

    public String getRated() {
        return mRated;
    }

    public void setRated(String rated) {
        this.mRated = rated;
    }

    public String getReleased() {
        return mReleased;
    }

    public void setReleased(String released) {
        this.mReleased = released;
    }

    public String getRuntime() {
        return mRuntime;
    }

    public void setRuntime(String runtime) {
        this.mRuntime = runtime;
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        this.mGenre = genre;
    }

    public String getDirector() {
        return mDirector;
    }

    public void setDirector(String director) {
        this.mDirector = director;
    }

    public String getWriter() {
        return mWriter;
    }

    public void setWriter(String writer) {
        this.mWriter = writer;
    }

    public String getActors() {
        return mActors;
    }

    public void setActors(String actors) {
        this.mActors = actors;
    }

    public String getPlot() {
        return mPlot;
    }

    public void setPlot(String plot) {
        this.mPlot = plot;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setLanguage(String language) {
        this.mLanguage = language;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public String getAwards() {
        return mAwards;
    }

    public void setAwards(String awards) {
        this.mAwards = awards;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.mPosterUrl = posterUrl;
    }

    public String getMetascore() {
        return mMetascore;
    }

    public void setMetascore(String metascore) {
        this.mMetascore = metascore;
    }

    public String getImdbRating() {
        return mImdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.mImdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return mImdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.mImdbVotes = imdbVotes;
    }

    public String getImdbId() {
        return mImdbId;
    }

    public void setImdbId(String imdbId) {
        this.mImdbId = imdbId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getDvd() {
        return mDvd;
    }

    public void setDvd(String dvd) {
        this.mDvd = dvd;
    }

    public String getBoxOffice() {
        return mBoxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.mBoxOffice = boxOffice;
    }

    public String getProduction() {
        return mProduction;
    }

    public void setProduction(String production) {
        this.mProduction = production;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        this.mWebsite = website;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        this.mResponse = response;
    }
}
