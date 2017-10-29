package pineapplesoftware.filmstock.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.OnInfiniteCyclePageTransformListener;

import java.util.ArrayList;

import pineapplesoftware.filmstock.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnInfiniteCyclePageTransformListener
{
    //region Attributes

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private Button mToolbarSearchButton;

    private HorizontalInfiniteCycleViewPager mMainInfiniteCycleViewPager;
    private CoverViewPagerAdapter mMoviesPagerAdapter;

    private int[] mLayouts;
    private ArrayList<String> mMovies = new ArrayList<>();

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

        mLayouts = new int[]{
                R.layout.item_movie_cover,
                R.layout.item_movie_cover,
                R.layout.item_movie_cover};

        getViews();
        setUpToolbar();
        loadUserSavedMovies();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_search_button:
                startActivity(MovieSearchActivity.getActivityIntent(this));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
                break;
//            case R.id.search_text:
//                startActivity(MovieSearchActivity.getActivityIntent(this));
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
//                break;
        }
    }

    @Override
    public void onPreTransform(View page, float position) {

    }

    @Override
    public void onPostTransform(View page, float position) {

    }

    //endregion

    //region Private Methods

    private void getViews() {
        mToolbar = findViewById(R.id.main_toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mToolbarSearchButton = mToolbar.findViewById(R.id.toolbar_search_button);

        mMainInfiniteCycleViewPager = findViewById(R.id.main_coverview);

        mToolbarSearchButton.setOnClickListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbarTitle.setText(getResources().getString(R.string.app_name));
        mToolbarSearchButton.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_search_white));
        mToolbarSearchButton.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    private void loadUserSavedMovies() {
        mMovies.add("https://images-na.ssl-images-amazon.com/images/M/MV5BMTkwNTczNTMyOF5BMl5BanBnXkFtZTcwNzUxOTUyMw@@._V1_SX300.jpg");
        mMovies.add("https://images-na.ssl-images-amazon.com/images/M/MV5BMTkwNTczNTMyOF5BMl5BanBnXkFtZTcwNzUxOTUyMw@@._V1_SX300.jpg");
        mMovies.add("https://images-na.ssl-images-amazon.com/images/M/MV5BMTkwNTczNTMyOF5BMl5BanBnXkFtZTcwNzUxOTUyMw@@._V1_SX300.jpg");
        mMovies.add("https://images-na.ssl-images-amazon.com/images/M/MV5BMTkwNTczNTMyOF5BMl5BanBnXkFtZTcwNzUxOTUyMw@@._V1_SX300.jpg");
        mMovies.add("https://images-na.ssl-images-amazon.com/images/M/MV5BMTkwNTczNTMyOF5BMl5BanBnXkFtZTcwNzUxOTUyMw@@._V1_SX300.jpg");
        mMovies.add("https://images-na.ssl-images-amazon.com/images/M/MV5BMTkwNTczNTMyOF5BMl5BanBnXkFtZTcwNzUxOTUyMw@@._V1_SX300.jpg");

        mMoviesPagerAdapter = new CoverViewPagerAdapter(this, mMovies);
        mMainInfiniteCycleViewPager.setAdapter(mMoviesPagerAdapter);
        mMoviesPagerAdapter.notifyDataSetChanged();
        mMainInfiniteCycleViewPager.setOnInfiniteCyclePageTransformListener(this);
    }

    //endregion

    //region Inner Classes

    class CoverViewPagerAdapter extends PagerAdapter implements View.OnClickListener {

        private LayoutInflater mLayoutInflater;
        private ArrayList<String> mMovies;
        private Context mContext;

        private CoverViewPagerAdapter(Context context, ArrayList<String> movies) {
            mMovies = movies;
            mContext = context;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = mLayoutInflater.inflate(R.layout.item_movie_cover, container, false);

            ImageView imageView = view.findViewById(R.id.main_item_movie_poster);
            Glide.with(mContext).load(mMovies.get(position)).into(imageView);

            container.addView(view);
            view.setOnClickListener(this);
            return view;
        }

        @Override
        public int getCount() {
            return mLayouts.length;
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
            startActivity(MovieDetailActivity.getActivityIntent(mContext));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
        }
    }

    //endregion
}
