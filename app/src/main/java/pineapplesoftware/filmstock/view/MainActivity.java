package pineapplesoftware.filmstock.view;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.OnInfiniteCyclePageTransformListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import pineapplesoftware.filmstock.R;
import pineapplesoftware.filmstock.helper.DatabaseHelper;
import pineapplesoftware.filmstock.model.dto.Movie;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnInfiniteCyclePageTransformListener
{
    //region Attributes

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private RelativeLayout mToolbarSearchButton;

    private LinearLayout mNoMoviesView;
    private HorizontalInfiniteCycleViewPager mMainInfiniteCycleViewPager;

    private LinearLayout mMainMovieInfoContainer;
    private TextView mMovieTitleTextView;
    private TextView mMovieInfoTextView;
    private TextView mMovieSavedDateTextView;

    private CoverViewPagerAdapter mMoviesPagerAdapter;

    private ArrayList<Movie> mMovies = new ArrayList<>();

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
                startActivity(MovieSearchActivity.getActivityIntent(this));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
                break;
            case R.id.main_movie_info_container:
                Movie selectedMovie = mMovies.get(mMainInfiniteCycleViewPager.getRealItem());
                startActivity(MovieDetailActivity.getActivityIntent(this, selectedMovie));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
                break;
        }
    }

    @Override
    public void onPreTransform(View page, float position) { }

    @Override
    public void onPostTransform(View page, float position) {
        crossFade();
    }

    //endregion

    //region Private Methods

    private void initViews() {
        mToolbar = findViewById(R.id.main_toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mToolbarSearchButton = mToolbar.findViewById(R.id.toolbar_main_relativelayout);

        mNoMoviesView = findViewById(R.id.main_no_movies_view);
        mMainInfiniteCycleViewPager = findViewById(R.id.main_coverview);

        mMainMovieInfoContainer = findViewById(R.id.main_movie_info_container);
        mMovieTitleTextView = findViewById(R.id.main_movie_title);
        mMovieInfoTextView = findViewById(R.id.main_movie_info);
        mMovieSavedDateTextView = findViewById(R.id.main_movie_saved_date);

        mToolbarSearchButton.setOnClickListener(this);
        mMainMovieInfoContainer.setOnClickListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbarTitle.setText(getResources().getString(R.string.app_name));
        mToolbarSearchButton.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    private void loadUserSavedMovies() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        mMovies = databaseHelper.getAllMovies();

        if (mMovies.size() > 0) {
            mMoviesPagerAdapter = new CoverViewPagerAdapter(this, mMovies);
            mMainInfiniteCycleViewPager.setAdapter(mMoviesPagerAdapter);
            mMoviesPagerAdapter.notifyDataSetChanged();
            mMainInfiniteCycleViewPager.setOnInfiniteCyclePageTransformListener(this);
        } else {
            mMainInfiniteCycleViewPager.setVisibility(View.GONE);
            mMainMovieInfoContainer.setVisibility(View.GONE);
            mNoMoviesView.setVisibility(View.VISIBLE);
        }
    }

    private void showMovieDetailedInfo(int position) {
        Movie movie = mMovies.get(position);
        startActivity(MovieDetailActivity.getActivityIntent(this, movie));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
    }

    private void crossFade() {
        AlphaAnimation anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(200);
        anim.setRepeatCount(1);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Movie focusedMovie = mMovies.get(mMainInfiniteCycleViewPager.getRealItem());
                mMovieTitleTextView.setText(focusedMovie.getTitle());

                String movieInfo = getResources().getString(R.string.main_movie_info);
                if (focusedMovie.getGenre() != null && !focusedMovie.getGenre().equals("") && !focusedMovie.getGenre().toLowerCase().equals("n/a")) {
                    movieInfo.replace("{genre}", focusedMovie.getGenre());
                } else {
                    movieInfo.replace("{genre} • ", "");
                }

                if (focusedMovie.getYear() != null && !focusedMovie.getYear().equals("") && !focusedMovie.getYear().toLowerCase().equals("n/a")) {
                    movieInfo.replace("{year}", focusedMovie.getYear());
                } else {
                    movieInfo.replace("{year} • ", "");
                }

                if (focusedMovie.getRuntime() != null && !focusedMovie.getRuntime().equals("") && !focusedMovie.getRuntime().toLowerCase().equals("n/a")) {
                    movieInfo.replace("{runtime}", focusedMovie.getRuntime());
                } else {
                    movieInfo.replace("• {runtime} ", "");
                }

                mMovieInfoTextView.setText(movieInfo
                        .replace("{genre}", focusedMovie.getGenre())
                        .replace("{year}", focusedMovie.getYear())
                        .replace("{runtime}", focusedMovie.getRuntime()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String date = dateFormat.format(focusedMovie.getDateSaved());
                mMovieSavedDateTextView.setText(getResources().getString(R.string.main_saved_on_text).replace("{date}", date));
            }
        });
        mMainMovieInfoContainer.startAnimation(anim);
    }

    //endregion

    //region Inner Classes

    class CoverViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

        private LayoutInflater mLayoutInflater;
        private ArrayList<Movie> mMovies;
        private Context mContext;

        private CoverViewPagerAdapter(Context context, ArrayList<Movie> movies) {
            mMovies = movies;
            mContext = context;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = mLayoutInflater.inflate(R.layout.item_movie_cover, container, false);

            ImageView imageView = view.findViewById(R.id.main_item_movie_poster);
            Glide.with(mContext).load(mMovies.get(position).getPosterUrl()).into(imageView);

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
        public void onClick(View view) {
            showMovieDetailedInfo(mMainInfiniteCycleViewPager.getRealItem());
        }
    }

    //endregion
}
