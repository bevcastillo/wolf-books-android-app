package com.bevstudio.wolfbooksapp.vendor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

public class InternetConnection {
    /*
    * noInternetLL is the "no internet connection" layout, parentLL is the layout of your main view that should be hidden
    */

    public static boolean isInternetConnected(Context context, View view, View mainView) {
        NetworkInfo networkInfo = (NetworkInfo) ((ConnectivityManager)
                                    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (networkInfo==null) {
            view.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);
            return false;
        }
        view.setVisibility(View.GONE);
        mainView.setVisibility(View.VISIBLE);
        return true;
    }
}
