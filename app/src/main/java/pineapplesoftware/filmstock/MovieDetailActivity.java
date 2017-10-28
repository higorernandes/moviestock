package pineapplesoftware.filmstock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MovieDetailActivity extends AppCompatActivity
{
    //region Attributes

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private Button mToolbarLikeButton;

    //endregion

    //region Constructors

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, MovieDetailActivity.class);
    }

    //endregion

    //region Overridden Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getViews();
        setUpToolbar();
        loadMovieInformation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
        return true;
    }

    //endregion

    //region Private Methods

    private void getViews() {
        mToolbar = findViewById(R.id.detail_toolbar);
        mToolbarTitle = mToolbar.findViewById(R.id.toolbar_title);
        mToolbarLikeButton = mToolbar.findViewById(R.id.toolbar_like_button);


    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        mToolbarTitle.setText(getResources().getString(R.string.movie_detail_text));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void loadMovieInformation() {

    }

    //endregion
}
