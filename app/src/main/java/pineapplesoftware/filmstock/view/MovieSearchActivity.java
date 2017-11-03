package pineapplesoftware.filmstock.view;

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
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import pineapplesoftware.filmstock.R;
import pineapplesoftware.filmstock.adapter.SearchResultsArrayAdapter;
import pineapplesoftware.filmstock.adapter.SearchResultsArrayAdapter.IMovieSelectionListener;
import pineapplesoftware.filmstock.model.domain.Search;
import pineapplesoftware.filmstock.model.dto.Movie;
import pineapplesoftware.filmstock.presenter.MovieSearchPresenter;

public class MovieSearchActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher,
        IMovieSelectionListener, IMovieSearchView
{
    //region Attributes

    private Toolbar mToolbar;
    private EditText mToolbarSearchEditText;
    private AVLoadingIndicatorView mLoadingProgressBar;
    private RelativeLayout mToolbarSearchButton;

    private RelativeLayout mNoInternetView;
    private RelativeLayout mNoItemsView;

    private RecyclerView mSearchResultsRecyclerView;
    private RelativeLayout mLoadingBottomContainer;
    private AVLoadingIndicatorView mLoadingBottomProgressBar;

    private SearchResultsArrayAdapter mSearchResultsArrayAdapter;
    private ArrayList<Search> mSearchResults = new ArrayList<>();

    private MovieSearchPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private int mPage = 1, mPastVisibleItems, mVisibleItemCount, mTotalItemCount;;
    private boolean mLoading = true;

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

        mPresenter = new MovieSearchPresenter();
        mPresenter.setView(this);

        initViews();
        setUpToolbar();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.no_internet_reconnect_button:
                mPresenter.searchMoviePaginated(mToolbarSearchEditText.getText().toString(), mPage);
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out_right);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mPage = 1;
        if (charSequence.length() >= 2) { // Minimum of 2 characters to start searching.
            mPresenter.searchMoviePaginated(charSequence.toString(), mPage);
        } else {
            mSearchResults.clear();
            mSearchResultsArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) { }

    @Override
    public void onMovieItemSelected(int position) {
        startActivity(MovieDetailActivity.getActivityIntent(this, mSearchResults.get(position).getImdbID()));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
    }

    //region MovieSearchPresenter Methods

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void requestCallbackSuccess() { }

    @Override
    public void requestCallbackError() {
        hideLoading();
        mSearchResultsRecyclerView.setVisibility(View.GONE);
        mNoItemsView.setVisibility(View.GONE);
        mNoInternetView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        mLoadingProgressBar.animate();
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mToolbarSearchButton.setVisibility(View.GONE);

        if (mPage > 1) {
            mLoadingBottomProgressBar.animate();
            mLoadingBottomContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        mLoadingProgressBar.clearAnimation();
        mLoadingProgressBar.setVisibility(View.GONE);

        mLoadingBottomProgressBar.clearAnimation();
        mLoadingBottomContainer.setVisibility(View.GONE);

        mToolbarSearchButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void callbackSuccessSearchMovie(final ArrayList<Movie> movieList) { }

    @Override
    public void callbackErrorSearchMovie() { }

    @Override
    public void callbackSuccessSearchMoviePaginated(final ArrayList<Search> searchResults) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoading = false;
                mNoInternetView.setVisibility(View.GONE);
                if (searchResults != null && searchResults.size() > 0) {
                    if (mPage == 1) {
                        mSearchResults.clear();
                    }

                    mNoItemsView.setVisibility(View.GONE);
                    mSearchResultsRecyclerView.setVisibility(View.VISIBLE);
                    mSearchResults.addAll(searchResults);
                    mSearchResultsArrayAdapter.notifyDataSetChanged();
                } else {
                    if (mPage == 1) {
                        mSearchResults.clear();
                        mSearchResultsRecyclerView.setVisibility(View.GONE);
                        mNoItemsView.setVisibility(View.VISIBLE);

                        TextView noItemsGreetingTextView = mNoItemsView.findViewById(R.id.no_items_greeting_text);
                        noItemsGreetingTextView.setText(getResources().getString(R.string.movie_search_no_results_greeting_text));

                        TextView noItemsErrorTextView = mNoItemsView.findViewById(R.id.no_items_error_text);
                        noItemsErrorTextView.setText(getResources().getString(R.string.movie_search_no_results_text));

                        TextView noItemsSuggestionTextView = mNoItemsView.findViewById(R.id.no_items_error_suggestion_text);
                        noItemsSuggestionTextView.setText(getResources().getString(R.string.movie_search_no_results_suggestion_text));
                    }
                }
            }
        });
    }

    @Override
    public void callbackErrorSearchMoviePaginated(String message) {
        mSearchResults.clear();
        mSearchResultsRecyclerView.setVisibility(View.GONE);
        mNoItemsView.setVisibility(View.VISIBLE);

        if (message.toLowerCase().contains("too many results")) {
            TextView noItemsGreetingTextView = mNoItemsView.findViewById(R.id.no_items_greeting_text);
            noItemsGreetingTextView.setText(getResources().getString(R.string.movie_search_too_many_results_greeting_text));

            TextView noItemsErrorTextView = mNoItemsView.findViewById(R.id.no_items_error_text);
            noItemsErrorTextView.setText(getResources().getString(R.string.movie_search_too_many_results_error_text));

            TextView noItemsSuggestionTextView = mNoItemsView.findViewById(R.id.no_items_error_suggestion_text);
            noItemsSuggestionTextView.setText(getResources().getString(R.string.movie_search_too_many_results_suggestion_text));
        } else if (message.toLowerCase().contains("movie not found")) {
            TextView noItemsGreetingTextView = mNoItemsView.findViewById(R.id.no_items_greeting_text);
            noItemsGreetingTextView.setText(getResources().getString(R.string.movie_search_no_results_greeting_text));

            TextView noItemsErrorTextView = mNoItemsView.findViewById(R.id.no_items_error_text);
            noItemsErrorTextView.setText(getResources().getString(R.string.movie_search_no_results_text));

            TextView noItemsSuggestionTextView = mNoItemsView.findViewById(R.id.no_items_error_suggestion_text);
            noItemsSuggestionTextView.setText(getResources().getString(R.string.movie_search_no_results_suggestion_text));
        }
    }

    //endregion

    //endregion

    //region Private Methods

    private void initViews() {
        mToolbar = findViewById(R.id.search_toolbar);
        mToolbarSearchEditText = mToolbar.findViewById(R.id.toolbar_search_edittext);
        mLoadingProgressBar = findViewById(R.id.toolbar_progressbar);
        mToolbarSearchButton = findViewById(R.id.toolbar_search_relativelayout);

        mNoInternetView = findViewById(R.id.search_no_internet);
        mNoItemsView = findViewById(R.id.search_no_items);
        Button reconnectButton = mNoInternetView.findViewById(R.id.no_internet_reconnect_button);

        mSearchResultsRecyclerView = findViewById(R.id.search_movies_recyclerview);
        mSearchResultsArrayAdapter = new SearchResultsArrayAdapter(this, mSearchResults);
        mSearchResultsArrayAdapter.setListener(this);
        mSearchResultsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mSearchResultsRecyclerView.setLayoutManager(mLayoutManager);
        mSearchResultsRecyclerView.setAdapter(mSearchResultsArrayAdapter);

        mLoadingBottomContainer = findViewById(R.id.search_progressbar_container);
        mLoadingBottomProgressBar = findViewById(R.id.search_bottom_progressbar);

        reconnectButton.setOnClickListener(this);
        mToolbarSearchEditText.addTextChangedListener(this);
        mSearchResultsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) {
                    mVisibleItemCount = mLayoutManager.getChildCount();
                    mTotalItemCount = mLayoutManager.getItemCount();
                    mPastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (!mLoading) {
                        if ((mVisibleItemCount + mPastVisibleItems) >= mTotalItemCount) {
                            mLoading = true;
                            mPage++;
                            mPresenter.searchMoviePaginated(mToolbarSearchEditText.getText().toString(), mPage);
                        }
                    }
                }
            }
        });

    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    //endregion
}
