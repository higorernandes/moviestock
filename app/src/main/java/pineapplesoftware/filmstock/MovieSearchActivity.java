package pineapplesoftware.filmstock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import pineapplesoftware.filmstock.SearchResultsArrayAdapter.IMovieSelectionListener;
import pineapplesoftware.filmstock.helper.NetworkHelper;

public class MovieSearchActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, IMovieSelectionListener
{
    //region Attributes

    private Toolbar mToolbar;
    private EditText mToolbarSearchEditText;
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
        performSearch("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no_internet_reconnect_button:
                performSearch(mToolbarSearchEditText.getText().toString());
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out_right);
                break;
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        performSearch(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) { }


    @Override
    public void onMovieItemSelected(int position) {
        startActivity(MovieDetailActivity.getActivityIntent(this));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
    }

    //endregion

    //region Private Methods

    private void initViews() {
        mToolbar = findViewById(R.id.search_toolbar);
        mToolbarSearchEditText = mToolbar.findViewById(R.id.toolbar_search_edittext);

        mNoInternetView = findViewById(R.id.search_no_internet);
        mNoItemsView = findViewById(R.id.search_no_items);
        Button reconnectButton = mNoInternetView.findViewById(R.id.no_internet_reconnect_button);

        mSearchResultsRecyclerView = findViewById(R.id.search_movies_recyclerview);
        mSearchResultsArrayAdapter = new SearchResultsArrayAdapter(mSearchResults);
        mSearchResultsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSearchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRecyclerView.setAdapter(mSearchResultsArrayAdapter);
        mSearchResultsArrayAdapter.setListener(this);

        reconnectButton.setOnClickListener(this);
        mToolbarSearchEditText.addTextChangedListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbarSearchEditText.setText(getResources().getString(R.string.movie_search_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void performSearch(String textToSearch) {
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
