package com.example.bookfinderapp;

/* Created by Beverly May Castillo on 8/14/20 */

import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtility {

    private static final String LOG_TAG = NetworkUtility.class.getSimpleName();
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?"; //base URI
    private static final String QUERY_PARAMS = "q"; //params for search string
    private static final String MAX_RES = "maxResults"; //params taht limits number of search results
    private static final String PRINT_TYPE = "printType"; //params to filter print type


    public static String getBookInfo(String queryString) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
            //building your query URI, limiting the results to 10 items and printed books
            Uri builtUri = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAMS, queryString)
                    .appendQueryParameter(MAX_RES, "10") //limiting search results to 10
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestURL = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.connect();

            //reading the response using an inputstream ans Stringbuffer, then convert it to String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line + "\n");

            }

            if (stringBuffer.length() == 0) {
                //stream was empty. No point in parsing
                return null;
            }

            bookJSONString = stringBuffer.toString();

        }catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (urlConnection != null) {
                try {
                    reader.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.d(LOG_TAG, bookJSONString);
            return bookJSONString;
        }
    }
}
