package pineapplesoftware.filmstock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import pineapplesoftware.filmstock.helper.NetworkHelper;

public class MovieSearchActivity extends AppCompatActivity implements View.OnClickListener
{
    //region Attributes

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private RelativeLayout mNoInternetView;
    private RelativeLayout mNoItemsView;

    private RecyclerView mSearchResultsRecyclerView;

    private SearchResultsArrayAdapter mSearchResultsArrayAdapter;
    private ArrayList<String> mSearchResults = new ArrayList<>();

    //endregion

    //region Constructors

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, MovieSearchActivity.class);
    }

    //endregion

    //region Overridden Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        initViews();
        setUpToolbar();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no_internet_reconnect_button:
                performSearch();
                break;
        }
    }

    //endregion

    //region Private Methods

    private void initViews() {
        mToolbar = findViewById(R.id.detail_toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);

        mNoInternetView = findViewById(R.id.search_no_internet);
        mNoItemsView = findViewById(R.id.search_no_items);
        Button reconnectButton = mNoInternetView.findViewById(R.id.no_internet_reconnect_button);

        SearchResultsArrayAdapter searchResultsArrayAdapter = new SearchResultsArrayAdapter(mSearchResults);
        mSearchResultsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSearchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRecyclerView.setAdapter(searchResultsArrayAdapter);

        reconnectButton.setOnClickListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbarTitle.setText(getResources().getString(R.string.movie_search_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void performSearch() {
        if (NetworkHelper.isConnected()) {
            mSearchResults.add("one");
            mSearchResults.add("one");
            mSearchResults.add("one");
            mSearchResults.add("one");
            mSearchResults.add("one");
            mSearchResults.add("one");
            mSearchResults.add("one");
            mSearchResults.add("one");
            mSearchResults.add("one");

            if (mSearchResults.size() > 0) {
                mSearchResultsRecyclerView.setVisibility(View.VISIBLE);
                mNoItemsView.setVisibility(View.GONE);
                mSearchResultsArrayAdapter.notifyDataSetChanged();
            } else {
                mSearchResultsRecyclerView.setVisibility(View.GONE);
                mNoItemsView.setVisibility(View.VISIBLE);
            }
        } else {
            mSearchResultsRecyclerView.setVisibility(View.GONE);
            mNoInternetView.setVisibility(View.VISIBLE);
        }
    }

    //endregion
}
