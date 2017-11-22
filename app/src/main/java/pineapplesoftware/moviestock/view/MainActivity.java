package pineapplesoftware.moviestock.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pineapplesoftware.moviestock.R;
import pineapplesoftware.moviestock.helper.DatabaseHelper;
import pineapplesoftware.moviestock.model.dto.Movie;
import pineapplesoftware.moviestock.util.CustomBounceInterpolator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener
{
    //region Attributes

    private Toolbar mToolbar;
    private RelativeLayout mToolbarSearchButton;

    private LinearLayout mNoMoviesView;
    private HorizontalInfiniteCycleViewPager mMainInfiniteCycleViewPager;

    private View mMainSeparatorView;

    private LinearLayout mMainMovieInfoContainer;
    private TextView mMovieTitleTextView;
    private TextView mMovieInfoTextView;
    private TextView mMovieSavedDateTextView;

    private CoverViewPagerAdapter mMoviesPagerAdapter;

    private final ArrayList<Movie> mMovies = new ArrayList<>();

    public static boolean sShouldReloadMoviesList = false;

    //endregion

    //region Constructors

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    //endregion

    //region Overridden Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setUpToolbar();
        loadUserSavedMovies();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sShouldReloadMoviesList) {
            loadUserSavedMovies();
            sShouldReloadMoviesList = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_main_relativelayout:
                // Animating the search button.
                final Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_smooth);
                CustomBounceInterpolator interpolator = new CustomBounceInterpolator(0.2, 3);
                bounceAnimation.setInterpolator(interpolator);
                bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        startActivity(MovieSearchActivity.getActivityIntent(MainActivity.this));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
                mToolbarSearchButton.startAnimation(bounceAnimation);
                break;
            case R.id.main_movie_info_container:
                Movie selectedMovie = mMovies.get(mMainInfiniteCycleViewPager.getRealItem());
                startActivity(MovieDetailActivity.getActivityIntent(this, selectedMovie.getImdbId()));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {
        loadMovieInfo();
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

    //endregion

    //region Private Methods

    private void initViews() {
        mToolbar = findViewById(R.id.main_toolbar);
        mToolbarSearchButton = mToolbar.findViewById(R.id.toolbar_main_relativelayout);

        mNoMoviesView = findViewById(R.id.main_no_movies_view);

        mMainInfiniteCycleViewPager = findViewById(R.id.main_coverview);
        mMoviesPagerAdapter = new CoverViewPagerAdapter(this, mMovies);
        mMainInfiniteCycleViewPager.setAdapter(mMoviesPagerAdapter);
        mMainInfiniteCycleViewPager.addOnPageChangeListener(this);

        mMainSeparatorView = findViewById(R.id.main_separator_view);

        mMainMovieInfoContainer = findViewById(R.id.main_movie_info_container);
        mMovieTitleTextView = findViewById(R.id.main_movie_title);
        mMovieInfoTextView = findViewById(R.id.main_movie_info);
        mMovieSavedDateTextView = findViewById(R.id.main_movie_saved_date);

        mToolbarSearchButton.setOnClickListener(this);
        mMainMovieInfoContainer.setOnClickListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbarSearchButton.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    /**
     * Loads users movies from the database.
     */
    private void loadUserSavedMovies() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        mMovies.clear();
        mMovies.addAll(databaseHelper.getAllMovies());

        if (mMovies.size() > 0) {
            mMoviesPagerAdapter = new CoverViewPagerAdapter(this, mMovies);
            mMainInfiniteCycleViewPager.setAdapter(mMoviesPagerAdapter);
            mMainInfiniteCycleViewPager.addOnPageChangeListener(this);
            mMoviesPagerAdapter.notifyDataSetChanged();
            mMainInfiniteCycleViewPager.setVisibility(View.VISIBLE);
            mMainMovieInfoContainer.setVisibility(View.VISIBLE);
            mMainSeparatorView.setVisibility(View.VISIBLE);
            mNoMoviesView.setVisibility(View.GONE);
            loadMovieInfo();
        } else {
            mMainInfiniteCycleViewPager.setVisibility(View.GONE);
            mMainMovieInfoContainer.setVisibility(View.GONE);
            mMainSeparatorView.setVisibility(View.GONE);
            mNoMoviesView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Loads the currently selected movie information into the bottom view.
     */
    private void loadMovieInfo() {
        Movie focusedMovie = mMovies.get(mMainInfiniteCycleViewPager.getRealItem());
        mMovieTitleTextView.setText(focusedMovie.getTitle());

        String movieInfo = getResources().getString(R.string.main_movie_info);
        if (focusedMovie.getGenre() != null && !focusedMovie.getGenre().equals("") && !focusedMovie.getGenre().toLowerCase().equals("n/a")) {
            movieInfo = movieInfo.replace("{genre}", focusedMovie.getGenre());
        } else {
            movieInfo = movieInfo.replace("{genre} • ", "");
        }

        if (focusedMovie.getYear() != null && !focusedMovie.getYear().equals("") && !focusedMovie.getYear().toLowerCase().equals("n/a")) {
            movieInfo = movieInfo.replace("{year}", focusedMovie.getYear());
        } else {
            movieInfo = movieInfo.replace("{year} • ", "");
        }

        if (focusedMovie.getRuntime() != null && !focusedMovie.getRuntime().equals("") && !focusedMovie.getRuntime().toLowerCase().equals("n/a")) {
            movieInfo = movieInfo.replace("{runtime}", focusedMovie.getRuntime());
        } else {
            movieInfo = movieInfo.replace("• {runtime}", "");
        }

        mMovieInfoTextView.setText(movieInfo);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = dateFormat.format(focusedMovie.getDateSaved());
        mMovieSavedDateTextView.setText(getResources().getString(R.string.main_saved_on_text).replace("{date}", date));
    }

    /**
     * Navigates to the MovieDetailActivity to show detailed movie information.
     * @param position The currently focused movie.
     */
    private void showMovieDetailedInfo(int position) {
        Movie movie = mMovies.get(position);
        startActivity(MovieDetailActivity.getActivityIntent(this, movie.getImdbId()));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
    }

    //endregion

    //region Inner Classes

    /**
     * A PagerAdapter for the movie covers.
     */
    class CoverViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

        private LayoutInflater mLayoutInflater;
        private final Context mContext;
        private final ArrayList<Movie> mItems;

        CoverViewPagerAdapter(Context context, ArrayList<Movie> items) {
            mContext = context;
            mItems = items;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = mLayoutInflater.inflate(R.layout.item_movie_cover, container, false);

            ImageView imageView = view.findViewById(R.id.main_item_movie_poster);
            RequestOptions requestOptions = new RequestOptions().placeholder(ContextCompat.getDrawable(mContext, R.drawable.ic_movie_placeholder));
            Glide.with(mContext).load(mItems.get(position).getPosterUrl()).apply(requestOptions).into(imageView);

            container.addView(view);
            view.setOnClickListener(this);
            return view;
        }

        @Override
        public int getCount() {
            return mMovies.size();
        }

        @Override
        public boolean isViewFromObject(final View view, final Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void onClick(final View view) {
            final Animation bounceAnimation = AnimationUtils.loadAnimation(mContext, R.anim.bounce_smooth_cover);
            CustomBounceInterpolator interpolator = new CustomBounceInterpolator(0.1, 1);
            bounceAnimation.setInterpolator(interpolator);
            bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    showMovieDetailedInfo(mMainInfiniteCycleViewPager.getRealItem());
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
            view.startAnimation(bounceAnimation);

        }
    }

    //endregion
}
