package pineapplesoftware.filmstock.model.domain;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieSearchResult implements Serializable
{
    @SerializedName("Search")
    @Expose
    private List<Search> mSearch = null;

    @SerializedName("totalResults")
    @Expose
    private String mTotalResults;

    @SerializedName("Response")
    @Expose
    private String mResponse;

    /**
     * No args constructor for use in serialization
     * 
     */
    public MovieSearchResult() { }

    /**
     * 
     * @param response
     * @param totalResults
     * @param search
     */
    public MovieSearchResult(List<Search> search, String totalResults, String response) {
        super();
        this.mSearch = search;
        this.mTotalResults = totalResults;
        this.mResponse = response;
    }

    public List<Search> getSearch() {
        return mSearch;
    }

    public void setSearch(List<Search> search) {
        this.mSearch = search;
    }

    public String getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(String totalResults) {
        this.mTotalResults = totalResults;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        this.mResponse = response;
    }

}
