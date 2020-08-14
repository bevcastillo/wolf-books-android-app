package com.example.bookfinderapp.models;

import android.net.Network;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.bookfinderapp.NetworkUtility;

import org.w3c.dom.Text;

/* Created by Beverly May Castillo on 8/14/20 */

public class FetchBook extends AsyncTask<String, Void, String> {

    public TextView mTitleText, mAuthorText;


    public FetchBook(TextView mTitleText, TextView mAuthorText) {
        this.mTitleText = mTitleText;
        this.mAuthorText = mAuthorText;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtility.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
