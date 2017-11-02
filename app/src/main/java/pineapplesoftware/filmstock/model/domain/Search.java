package pineapplesoftware.filmstock.model.domain;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search implements Serializable
{
    @SerializedName("Title")
    @Expose
    private String mTitle;

    @SerializedName("Year")
    @Expose
    private String mYear;

    @SerializedName("imdbID")
    @Expose
    private String mImdbId;

    @SerializedName("Type")
    @Expose
    private String mType;

    @SerializedName("Poster")
    @Expose
    private String mPosterUrl;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Search() { }

    /**
     * 
     * @param title
     * @param poster
     * @param year
     * @param imdbID
     * @param type
     */
    public Search(String title, String year, String imdbID, String type, String poster) {
        super();
        this.mTitle = title;
        this.mYear = year;
        this.mImdbId = imdbID;
        this.mType = type;
        this.mPosterUrl = poster;
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

    public String getImdbID() {
        return mImdbId;
    }

    public void setImdbID(String imdbID) {
        this.mImdbId = imdbID;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public void setPosterUrl(String poster) {
        this.mPosterUrl = poster;
    }

}
