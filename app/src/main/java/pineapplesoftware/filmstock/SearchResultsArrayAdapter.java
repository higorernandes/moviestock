package pineapplesoftware.filmstock;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by root on 2017-10-28.
 */

public class SearchResultsArrayAdapter extends RecyclerView.Adapter<SearchResultsArrayAdapter.MoviesHolder>
{
    //region Attributes

    private ArrayList<String> mObjects;

    //endregion

    //region Constructor

    public SearchResultsArrayAdapter(ArrayList<String> searchResults) {
        mObjects = searchResults;
    }

    //endregion

    //region Overridden Methods

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new MoviesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    //endregion

    //region Inner Classes

    class MoviesHolder extends RecyclerView.ViewHolder
    {
        public MoviesHolder(View itemView) {
            super(itemView);
        }
    }

    //endregion
}
