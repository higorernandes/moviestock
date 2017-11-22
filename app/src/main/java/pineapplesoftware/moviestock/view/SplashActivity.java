package pineapplesoftware.moviestock.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import pineapplesoftware.moviestock.R;

public class SplashActivity extends AppCompatActivity
{
    //region Overridden Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        final ImageView splashImage = findViewById(R.id.splash_moviestock_imageview);

        // Setting up the animations.
        final Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                splashImage.startAnimation(fadeOutAnimation);
                            }
                        });
                    }
                }, 2000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashImage.setVisibility(View.GONE);
                startActivity(MainActivity.getActivityIntent(SplashActivity.this));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        splashImage.startAnimation(fadeInAnimation);
    }

    //endregion
}
