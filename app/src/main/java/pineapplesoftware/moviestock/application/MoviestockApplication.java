package pineapplesoftware.moviestock.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class MoviestockApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static MoviestockApplication mInstance;

    public static Context getAppContext() {
        return mContext;
    }

    public static synchronized MoviestockApplication getInstance() {
        return mInstance;
    }

    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mInstance = this;
    }
}