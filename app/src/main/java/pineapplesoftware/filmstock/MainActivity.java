package pineapplesoftware.filmstock;

import android.content.Context;
import android.content.Intent;
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

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    //region Attributes

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private Button mToolbarLikeButton;

    private int[] mLayouts;

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
            case R.id.detail_text:
                startActivity(MovieDetailActivity.getActivityIntent(this));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
                break;
            case R.id.search_text:
                startActivity(MovieSearchActivity.getActivityIntent(this));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out);
                break;
        }
    }

    //endregion

    //region Private Methods

    private void getViews() {
        mToolbar = findViewById(R.id.main_toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mToolbarLikeButton = mToolbar.findViewById(R.id.toolbar_like_button);

        TextView detail = findViewById(R.id.detail_text);
        TextView search = findViewById(R.id.search_text);
        detail.setOnClickListener(this);
        search.setOnClickListener(this);
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbarTitle.setText(getResources().getString(R.string.app_name));
        mToolbarLikeButton.setVisibility(View.GONE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    private void loadUserSavedMovies() {
        final HorizontalInfiniteCycleViewPager infiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) findViewById(R.id.main_coverview);

        infiniteCycleViewPager.setAdapter(new OnboardingViewPagerAdapter());
        infiniteCycleViewPager.setScrollDuration(500);
        infiniteCycleViewPager.setMediumScaled(true);
        infiniteCycleViewPager.setMaxPageScale(0.8F);
        infiniteCycleViewPager.setMinPageScale(0.5F);
        infiniteCycleViewPager.setCenterPageScaleOffset(30.0F);
        infiniteCycleViewPager.setMinPageScaleOffset(5.0F);
        infiniteCycleViewPager.notifyDataSetChanged();
//        infiniteCycleViewPager.setOnInfiniteCyclePageTransformListener(...);
    }

    //endregion

    //region Inner Classes

    class OnboardingViewPagerAdapter extends PagerAdapter {

        private LayoutInflater mLayoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mLayoutInflater.inflate(mLayouts[position], container, false);

            ImageView imageView = view.findViewById(R.id.main_item_movie_poster);

//            TextView titleTextView = (TextView) view.findViewById(R.id.onboarding_title_textview);
//            titleTextView.setTypeface(sUbuntuFontMedium);
//            TextView descriptionTextView = (TextView) view.findViewById(R.id.onboarding_description_textview);
//            descriptionTextView.setTypeface(sUbuntuFontRegular);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return mLayouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    //endregion
}
