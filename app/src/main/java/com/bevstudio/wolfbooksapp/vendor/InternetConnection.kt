package com.bevstudio.wolfbooksapp.vendor

import android.content.Context
import android.net.ConnectivityManager
import android.view.View

object InternetConnection {
    /*
    * noInternetLL is the "no internet connection" layout, parentLL is the layout of your main view that should be hidden
    */
    fun isInternetConnected(context: Context, view: View, mainView: View): Boolean {
        if ((context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo == null) {
            view.visibility = View.VISIBLE
            mainView.visibility = View.GONE
            return false
        }
        view.visibility = View.GONE
        mainView.visibility = View.VISIBLE
        return true
    }
}
