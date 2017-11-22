package pineapplesoftware.moviestock.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import pineapplesoftware.moviestock.application.MoviestockApplication;

public class NetworkHelper
{
    /**
     * Checks whether there's an active internet connection at the current moment.
     * @return TRUE if there's internet, FALSE if you're offline.
     */
    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MoviestockApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }

        return false;
    }
}
