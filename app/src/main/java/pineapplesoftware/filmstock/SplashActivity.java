package pineapplesoftware.filmstock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity
{
    //region Overridden Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                navigateToMainScreen();
            }
        }, 5000);
    }

    //endregion

    //region Private Methods

    private void navigateToMainScreen() {
        startActivity(MainActivity.getActivityIntent(this));
    }

    //endregion
}
