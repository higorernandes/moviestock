package pineapplesoftware.filmstock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    //region Attributes

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

        getViews();
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
        TextView detail = findViewById(R.id.detail_text);
        TextView search = findViewById(R.id.search_text);
        detail.setOnClickListener(this);
        search.setOnClickListener(this);
    }

//    private void setUpToolbar() {
//        setSupportActionBar(mToolbar);
//        mToolbarTitle.setText(getResources().getString(R.string.movie_detail_text));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//    }

    //endregion
}
