package com.example.bookfinderapp.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.LinearLayout;

public class InternetConnection {
    /*
    * noInternetLL is the "no internet connection" layout, parentLL is the layout of your main view that should be hidden
    */

    public static boolean isInternetConnected(Context context, LinearLayout noInternetLL, LinearLayout parentLL) {
        NetworkInfo networkInfo = (NetworkInfo) ((ConnectivityManager)
                                    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (networkInfo==null) {
            noInternetLL.setVisibility(View.VISIBLE);
            parentLL.setVisibility(View.GONE);
            return false;
        }
        noInternetLL.setVisibility(View.GONE);
        parentLL.setVisibility(View.VISIBLE);
        return true;
    }
}
