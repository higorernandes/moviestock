package pineapplesoftware.filmstock.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class FilmstockApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static FilmstockApplication mInstance;

    public static Context getAppContext() {
        return mContext;
    }

    public static synchronized FilmstockApplication getInstance() {
        return mInstance;
    }

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mInstance = this;
    }
}