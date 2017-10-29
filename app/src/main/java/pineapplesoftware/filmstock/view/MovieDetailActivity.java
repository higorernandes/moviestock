package pineapplesoftware.filmstock.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pineapplesoftware.filmstock.R;
import pineapplesoftware.filmstock.model.dto.Movie;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener
{
    //region Attributes

    private static final String TITLE = "TITLE";
    private static final String YEAR = "YEAR";
    private static final String RATED = "RATED";
    private static final String RELEASED = "RELEASED";
    private static final String RUNTIME = "RUNTIME";
    private static final String GENRE = "GENRE";
    private static final String DIRECTOR = "DIRECTOR";
    private static final String WRITER = "WRITER";
    private static final String ACTORS = "ACTORS";
    private static final String PLOT = "PLOT";
    private static final String LANGUAGE = "LANGUAGE";
    private static final String COUNTRY = "COUNTRY";
    private static final String AWARDS = "AWARDS";
    private static final String POSTER_URL = "POSTER_URL";
    private static final String METASCORE = "METASCORE";
    private static final String IMDB_RATING = "IMDB_RATING";
    private static final String IMDB_VOTES = "IMDB_VOTES";
    private static final String IMDB_ID = "IMDB_ID";
    private static final String TYPE = "TYPE";
    private static final String DVD = "DVD";
    private static final String BOX_OFFICE = "BOX_OFFICE";
    private static final String PRODUCTION = "PRODUCTION";
    private static final String WEBSITE = "WEBSITE";

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private Button mToolbarLikeButton;

    //endregion

    //region Constructors

    public static Intent getActivityIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(TITLE, movie.getTitle());
        intent.putExtra(YEAR, movie.getYear());
        intent.putExtra(RATED, movie.getRated());
        intent.putExtra(RELEASED, movie.getReleased());
        intent.putExtra(RUNTIME, movie.getRuntime());
        intent.putExtra(GENRE, movie.getGenre());
        intent.putExtra(DIRECTOR, movie.getDirector());
        intent.putExtra(WRITER, movie.getWriter());
        intent.putExtra(ACTORS, movie.getActors());
        intent.putExtra(PLOT, movie.getPlot());
        intent.putExtra(LANGUAGE, movie.getLanguage());
        intent.putExtra(COUNTRY, movie.getCountry());
        intent.putExtra(AWARDS, movie.getAwards());
        intent.putExtra(POSTER_URL, movie.getPosterUrl());
        intent.putExtra(METASCORE, movie.getMetascore());
        intent.putExtra(IMDB_RATING, movie.getImdbRating());
        intent.putExtra(IMDB_VOTES, movie.getImdbVotes());
        intent.putExtra(IMDB_ID, movie.getImdbId());
        intent.putExtra(TYPE, movie.getType());
        intent.putExtra(DVD, movie.getDvd());
        intent.putExtra(BOX_OFFICE, movie.getBoxOffice());
        intent.putExtra(PRODUCTION, movie.getProduction());
        intent.putExtra(WEBSITE, movie.getWebsite());

        return intent;
    }

    //endregion

    //region Overridden Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initViews();
        setUpToolbar();
        loadMovieInformation();
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
            case R.id.toolbar_like_button:
                saveMovieToLocalDatabase();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out_right);
    }

    //endregion

    //region Private Methods

    private void initViews() {
        mToolbar = findViewById(R.id.detail_toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mToolbarLikeButton = mToolbar.findViewById(R.id.toolbar_like_button);

        //region Main information

        ImageView moviePosterImageView = findViewById(R.id.detail_movie_image);
        Glide.with(this).load(getIntent().getStringExtra(POSTER_URL)).into(moviePosterImageView);

        TextView movieTitleTextView = findViewById(R.id.detail_movie_name);
        movieTitleTextView.setText(getIntent().getStringExtra(TITLE));

        TextView movieGenreTextView = findViewById(R.id.detail_movie_genre_text);
        movieGenreTextView.setText(getIntent().getStringExtra(GENRE));

        TextView movieDurationTextView = findViewById(R.id.detail_movie_duration);
        movieDurationTextView.setText(getResources().getString(R.string.movie_detail_movie_year_runtime)
                .replace("{movieYear}", getIntent().getStringExtra(YEAR))
                .replace("{runtime}", getIntent().getStringExtra(RUNTIME)));

        TextView moviePlotTextView = findViewById(R.id.detail_movie_plot);
        moviePlotTextView.setText(getIntent().getStringExtra(PLOT));

        TextView movieDirectorTextView = findViewById(R.id.detail_movie_director_text);
        movieDirectorTextView.setText(getIntent().getStringExtra(DIRECTOR));

        //endregion

        //region Secondary information

        TextView movieWriterTextView = findViewById(R.id.detail_movie_writer_text);
        movieWriterTextView.setText(getIntent().getStringExtra(WRITER));

        TextView movieRatedTextView = findViewById(R.id.detail_movie_rated_text);
        movieRatedTextView.setText(getIntent().getStringExtra(RATED));

        TextView movieLanguageTextView = findViewById(R.id.detail_movie_language_text);
        movieLanguageTextView.setText(getIntent().getStringExtra(LANGUAGE));

        TextView movieCountryTextView = findViewById(R.id.detail_movie_country_text);
        movieCountryTextView.setText(getIntent().getStringExtra(COUNTRY));

        TextView movieProductionTextView = findViewById(R.id.detail_movie_production_text);
        movieProductionTextView.setText(getIntent().getStringExtra(PRODUCTION));

        TextView movieBoxOfficeTextView = findViewById(R.id.detail_movie_box_text);
        movieBoxOfficeTextView.setText(getIntent().getStringExtra(BOX_OFFICE));

        TextView movieRatingsTextView = findViewById(R.id.detail_movie_ratings_text);
        movieRatingsTextView.setText(getIntent().getStringExtra(IMDB_RATING));

        //endregion

        mToolbarLikeButton.setOnClickListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbarTitle.setText(getResources().getString(R.string.movie_detail_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarLikeButton.setVisibility(View.VISIBLE);
    }

    private void loadMovieInformation() {

    }

    private void saveMovieToLocalDatabase() {

    }

    //endregion
}
