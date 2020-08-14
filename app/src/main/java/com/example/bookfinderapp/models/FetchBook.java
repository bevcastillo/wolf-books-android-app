package com.example.bookfinderapp.models;

import android.net.Network;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.bookfinderapp.NetworkUtility;

import org.json.JSONArray;
import org.json.JSONObject;
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

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i=0; i<itemsArray.length(); i++) {
                JSONObject book = itemsArray.getJSONObject(i);
                String title = null;
                String authors = null;

                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                }catch (Exception e) {
                    e.printStackTrace();
                }

                //if both title and author exists, update the Textview and return
                if (title != null && authors !=null) {
                    mTitleText.setText(title);
                    mAuthorText.setText(authors);

                    return;
                }
            }

            mTitleText.setText("No Results Found");

        }catch (Exception e) {
            mTitleText.setText("No Results Found");
            mAuthorText.setText("");
            e.printStackTrace();
        }


    }
}
