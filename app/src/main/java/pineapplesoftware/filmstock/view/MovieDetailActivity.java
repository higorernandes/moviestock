package pineapplesoftware.filmstock.view;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Calendar;

import pineapplesoftware.filmstock.R;
import pineapplesoftware.filmstock.helper.DatabaseHelper;
import pineapplesoftware.filmstock.model.dto.Movie;
import pineapplesoftware.filmstock.presenter.MovieDetailPresenter;
import pineapplesoftware.filmstock.util.CustomBounceInterpolator;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener, IMovieDetailView
{
    //region Attributes

    private static final String IMDB_ID = "ID";

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private RelativeLayout mToolbarLikeButton;
    private ImageView mToolbarLikeImage;

    private ScrollView mMainScrollView;
    private RelativeLayout mNoInternetView;
    private Button mNoInternetReconnectButton;
    private RelativeLayout mLoadingView;

    private ImageView mMoviePosterImageView;
    private ImageButton mPlayImageButton;
    private TextView mMovieTitleTextView;
    private TextView mMovieTypeTextView;

    private TextView mMovieGenreTextView;
    private TextView mMovieDurationTextView;
    private TextView mMoviePlotTextView;
    private TextView mMovieActorsTextView;
    private TextView mMovieDirectorTextView;

    private TextView mMovieWriterTextView;
    private TextView mMovieRatedTextView;
    private TextView mMovieAwardsTextView;
    private TextView mMovieLanguageTextView;
    private TextView mMovieCountryTextView;
    private TextView mMovieProductionTextView;
    private TextView mMovieReleasedTextView;
    private TextView mMovieBoxOfficeTextView;
    private TextView mMovieRatingsTextView;

    private Movie mMovie = new Movie();
    private String mMovieImdbId;
    private MovieDetailPresenter mPresenter;

    //endregion

    //region Constructors

    public static Intent getActivityIntent(Context context, String imdbId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(IMDB_ID, imdbId);

        return intent;
    }

    //endregion

    //region Overridden Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mPresenter = new MovieDetailPresenter();
        mPresenter.setView(this);

        initViews();
        setUpToolbar();
        loadLocalMovieInformation();
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_detail_like_relativelayout:
                saveOrUnsaveMovieToLocalDatabase();
                break;
            case R.id.detail_title_play_imagebutton:
                openYoutubeVideoSearch();
                break;
            case R.id.no_internet_reconnect_button:
                mPresenter.loadMovieInformation(mMovieImdbId);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out_right);
    }

    //region MovieDetailPresenter Methods

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void requestCallbackSuccess() { }

    @Override
    public void requestCallbackError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMainScrollView.setVisibility(View.GONE);
                mNoInternetView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void callbackSuccessLoadMovie(final Movie movie) {
        mMovie = movie;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNoInternetView.setVisibility(View.GONE);
                mMainScrollView.setVisibility(View.VISIBLE);
                loadMovieInformationIntoViews(movie);
            }
        });
    }

    @Override
    public void callbackErrorLoadMovie() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMainScrollView.setVisibility(View.GONE);
                mNoInternetView.setVisibility(View.VISIBLE);

                TextView noInternetTextView = mNoInternetView.findViewById(R.id.no_internet_text);
                noInternetTextView.setText(getResources().getString(R.string.generic_server_error));

                TextView noInternetSuggestionTextView = mNoInternetView.findViewById(R.id.no_internet_suggestion);
                noInternetSuggestionTextView.setText(getResources().getString(R.string.generic_server_connection_error_suggestion));

                ImageView noInternetImageView = mNoInternetView.findViewById(R.id.loading_indicator);
                noInternetImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_shrug));
            }
        });
    }

    //endregion

    //endregion

    //region Private Methods

    private void initViews() {
        mToolbar = findViewById(R.id.detail_toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mToolbarLikeButton = mToolbar.findViewById(R.id.toolbar_detail_like_relativelayout);
        mToolbarLikeImage = mToolbar.findViewById(R.id.toolbar_detail_like_imageview);

        mMainScrollView = findViewById(R.id.detail_main_scrollview);
        mNoInternetView = findViewById(R.id.detail_no_internet_view);
        mNoInternetReconnectButton = findViewById(R.id.no_internet_reconnect_button);
        mLoadingView = findViewById(R.id.detail_loading_view);

        mMoviePosterImageView = findViewById(R.id.detail_movie_image);
        mPlayImageButton = findViewById(R.id.detail_title_play_imagebutton);
        mMovieTitleTextView = findViewById(R.id.detail_movie_name);
        mMovieTypeTextView = findViewById(R.id.detail_movie_type);

        mMovieGenreTextView = findViewById(R.id.detail_movie_genre_text);
        mMovieDurationTextView = findViewById(R.id.detail_movie_duration);
        mMoviePlotTextView = findViewById(R.id.detail_movie_plot);
        mMovieActorsTextView = findViewById(R.id.detail_movie_actors_text);
        mMovieDirectorTextView = findViewById(R.id.detail_movie_director_text);

        mMovieWriterTextView = findViewById(R.id.detail_movie_writer_text);
        mMovieRatedTextView = findViewById(R.id.detail_movie_rated_text);
        mMovieAwardsTextView = findViewById(R.id.detail_movie_awards_text);
        mMovieLanguageTextView = findViewById(R.id.detail_movie_language_text);
        mMovieCountryTextView = findViewById(R.id.detail_movie_country_text);
        mMovieProductionTextView = findViewById(R.id.detail_movie_production_text);
        mMovieReleasedTextView = findViewById(R.id.detail_movie_released_text);
        mMovieBoxOfficeTextView = findViewById(R.id.detail_movie_box_text);
        mMovieRatingsTextView = findViewById(R.id.detail_movie_ratings_text);

        mToolbarLikeButton.setOnClickListener(this);
        mPlayImageButton.setOnClickListener(this);
        mNoInternetReconnectButton.setOnClickListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbarTitle.setText(getResources().getString(R.string.movie_detail_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarLikeButton.setVisibility(View.VISIBLE);
    }

    /**
     * Loads the movie information from the local database. If movie doesn't exist locally, it loads
     * it from the API.
     */
    private void loadLocalMovieInformation() {
        mMovieImdbId = getIntent().getStringExtra(IMDB_ID);

        DatabaseHelper database = new DatabaseHelper(this);
        mMovie = database.getMovieWithImdbId(mMovieImdbId);

        if (mMovie != null) {
            // Setting the "Liked" button.
            mToolbarLikeImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_filled));
            loadMovieInformationIntoViews(mMovie);
        } else {
            mPresenter.loadMovieInformation(mMovieImdbId);
        }
    }

    /**
     * Loads a given movie's information into the views.
     * @param movie The movie object containing the information.
     */
    private void loadMovieInformationIntoViews(Movie movie) {

        //region Main information

        LinearLayout containerView;

        // Poster.
        if (movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty() && !movie.getPosterUrl().toLowerCase().equals("n/a")) {
            Glide.with(this).load(movie.getPosterUrl()).into(mMoviePosterImageView);
        }

        // Title.
        if (movie.getTitle() != null && !movie.getTitle().isEmpty() && !movie.getTitle().toLowerCase().equals("n/a")) {
            mMovieTitleTextView.setText(movie.getTitle());
        }

        // Type.
        if (movie.getType() != null && !movie.getType().isEmpty() && !movie.getType().toLowerCase().equals("n/a")) {
            mMovieTypeTextView.setText(movie.getType());
        } else {
            mMovieTypeTextView.setVisibility(View.GONE);
        }

        // Genre.
        if (movie.getGenre() != null && !movie.getGenre().isEmpty() && !movie.getGenre().toLowerCase().equals("n/a")) {
            mMovieGenreTextView.setText(movie.getGenre());
        }

        // Year and Runtime/duration.
        if ((movie.getYear() != null && !movie.getYear().isEmpty() && !movie.getYear().toLowerCase().equals("n/a")) && (movie.getRuntime() != null && !movie.getRuntime().isEmpty() && !movie.getRuntime().toLowerCase().equals("n/a"))) {
            mMovieDurationTextView.setText(getResources().getString(R.string.movie_detail_movie_year_runtime)
                    .replace("{movieYear}", movie.getYear())
                    .replace("{runtime}", movie.getRuntime()));
        } else if ((movie.getYear() == null || movie.getYear().isEmpty() || movie.getYear().toLowerCase().equals("n/a")) && (movie.getRuntime() != null && !movie.getRuntime().isEmpty() && !movie.getRuntime().toLowerCase().equals("n/a"))) {
            mMovieDurationTextView.setText(movie.getRuntime());
        } else if ((movie.getYear() != null && !movie.getYear().isEmpty() && !movie.getYear().toLowerCase().equals("n/a")) && (movie.getRuntime() == null || movie.getRuntime().isEmpty() || movie.getRuntime().toLowerCase().equals("n/a"))) {
            mMovieDurationTextView.setText(movie.getYear());
        }

        // Plot.
        if (movie.getPlot() != null && !movie.getPlot().isEmpty() && !movie.getPlot().toLowerCase().equals("n/a")) {
            mMoviePlotTextView.setText(movie.getPlot());
        }

        // Cast.
        if (movie.getActors() != null && !movie.getActors().isEmpty() && !movie.getActors().toLowerCase().equals("n/a")) {
            mMovieActorsTextView.setText(movie.getActors());
        } else {
            containerView = findViewById(R.id.detail_actors_container);
            containerView.setVisibility(View.GONE);
        }

        // Director.
        if (movie.getDirector() != null && !movie.getDirector().isEmpty() && !movie.getDirector().toLowerCase().equals("n/a")) {
            mMovieDirectorTextView.setText(movie.getDirector());
        } else {
            containerView = findViewById(R.id.detail_director_container);
            containerView.setVisibility(View.GONE);
        }

        //endregion

        //region Secondary information

        // Writers.
        if (movie.getWriter() != null && !movie.getWriter().isEmpty() && !movie.getWriter().toLowerCase().equals("n/a")) {
            mMovieWriterTextView.setText(movie.getWriter());
        } else {
            containerView = findViewById(R.id.detail_writer_container);
            containerView.setVisibility(View.GONE);
        }

        // Rated.
        if (movie.getRated() != null && !movie.getRated().isEmpty() && !movie.getRated().toLowerCase().equals("n/a")) {
            mMovieRatedTextView.setText(movie.getRated());
        } else {
            containerView = findViewById(R.id.detail_rated_container);
            containerView.setVisibility(View.GONE);
        }

        // Awards.
        if (movie.getAwards() != null && !movie.getAwards().isEmpty() && !movie.getAwards().toLowerCase().equals("n/a")) {
            mMovieAwardsTextView.setText(movie.getAwards());
        } else {
            containerView = findViewById(R.id.detail_awards_container);
            containerView.setVisibility(View.GONE);
        }

        // Language.
        if (movie.getLanguage() != null && !movie.getLanguage().isEmpty() && !movie.getLanguage().toLowerCase().equals("n/a")) {
            mMovieLanguageTextView.setText(movie.getLanguage());
        } else {
            containerView = findViewById(R.id.detail_language_container);
            containerView.setVisibility(View.GONE);
        }

        // Country.
        if (movie.getCountry() != null && !movie.getCountry().isEmpty() && !movie.getCountry().toLowerCase().equals("n/a")) {
            mMovieCountryTextView.setText(movie.getCountry());
        } else {
            containerView = findViewById(R.id.detail_country_container);
            containerView.setVisibility(View.GONE);
        }

        // Production.
        if (movie.getProduction() != null && !movie.getProduction().isEmpty() && !movie.getProduction().toLowerCase().equals("n/a")) {
            mMovieProductionTextView.setText(movie.getProduction());
        } else {
            containerView = findViewById(R.id.detail_production_container);
            containerView.setVisibility(View.GONE);
        }

        // Released.
        if (movie.getReleased() != null && !movie.getReleased().isEmpty() && !movie.getReleased().toLowerCase().equals("n/a")) {
            mMovieReleasedTextView.setText(movie.getReleased());
        } else {
            containerView = findViewById(R.id.detail_released_container);
            containerView.setVisibility(View.GONE);
        }

        // Box Office.
        if (movie.getBoxOffice() != null && !movie.getBoxOffice().isEmpty() && !movie.getBoxOffice().toLowerCase().equals("n/a")) {
            mMovieBoxOfficeTextView.setText(movie.getBoxOffice());
        } else {
            containerView = findViewById(R.id.detail_boxoffice_container);
            containerView.setVisibility(View.GONE);
        }

        // IMDB Rating.
        if (movie.getImdbRating() != null && !movie.getImdbRating().isEmpty() && !movie.getImdbRating().toLowerCase().equals("n/a")) {
            mMovieRatingsTextView.setText(movie.getImdbRating());
        } else {
            containerView = findViewById(R.id.detail_ratings_container);
            containerView.setVisibility(View.GONE);
        }
    }

    /**
     * Saves or deletes a movie from the local database (you don't say?).
     */
    private void saveOrUnsaveMovieToLocalDatabase() {
        if (mLoadingView.getVisibility() == View.GONE) {
            DatabaseHelper database = new DatabaseHelper(this);

            Movie movie = database.getMovieWithImdbId(mMovie.getImdbId());

            if (movie != null) {
                database.deleteMovie(movie);

                // Setting up the bouncing animation.
                final Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
                CustomBounceInterpolator interpolator = new CustomBounceInterpolator(0.2, 20);
                bounceAnimation.setInterpolator(interpolator);
                bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mToolbarLikeImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_border_white));
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) { }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });

                mToolbarLikeImage.startAnimation(bounceAnimation);

                Toast.makeText(this, getResources().getString(R.string.movie_detail_success_unsave_message), Toast.LENGTH_SHORT).show();
            } else {
                mMovie.setDateSaved(Calendar.getInstance().getTime());
                database.addMovie(mMovie);
                database.getMovieWithImdbId(mMovie.getImdbId());

                // Setting up the bouncing animation.
                final Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
                CustomBounceInterpolator interpolator = new CustomBounceInterpolator(0.2, 20);
                bounceAnimation.setInterpolator(interpolator);
                bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mToolbarLikeImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favorite_filled));
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) { }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });

                mToolbarLikeImage.startAnimation(bounceAnimation);

                Toast.makeText(this, getResources().getString(R.string.movie_detail_success_save_message), Toast.LENGTH_SHORT).show();
            }

            MainActivity.sShouldReloadMoviesList = true;
        } else {
            Toast.makeText(this, getResources().getString(R.string.movie_detail_wait_message), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Attempts to open the youtube app to search for a video. In case it fails, opens it via browser.
     */
    private void openYoutubeVideoSearch() {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mMovie.getTitle()));
        appIntent.setPackage("com.google.android.youtube");
        appIntent.putExtra("query", mMovie.getTitle() + " trailer");
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String movieSearch = mMovie.getTitle().trim().replaceAll(" ", "+") + "+trailer";
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/results?search_query=" + movieSearch));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    //endregion
}
