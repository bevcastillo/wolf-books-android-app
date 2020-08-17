package com.example.bookfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookfinderapp.viewmodels.SearchResultsActivity;

/* Created by Beverly May Castillo on 8/12/20 */


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText et_search_input;
    private Button btn_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_search_input = findViewById(R.id.et_search);
        btn_search = findViewById(R.id.button);
        btn_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button:
//                searchBooks();

                getVolumeResponse();
                break;
        }
    }

    private void getVolumeResponse() {

        String queryString = et_search_input.getText().toString();

        //get the queryString data and pass it to SearchResultsActivity
        Intent queryIntent = new Intent(this, SearchResultsActivity.class);
        queryIntent.putExtra("query_string", queryString);
        startActivity(queryIntent);


    }

}
